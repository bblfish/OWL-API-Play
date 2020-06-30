import $ivy.`net.sourceforge.owlapi:owlapi-distribution:5.1.9`

import scala.collection.mutable
//import $ivy.`org.scala-lang.modules::scala-collection-contrib:0.2.1`
import org.semanticweb.owlapi.model._
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.profiles._
import java.net.URL

import ammonite.ops._

import org.semanticweb.owlapi.profiles._

import scala.jdk.CollectionConverters._
import scala.jdk.StreamConverters._


case class Reports(ontId: OWLOntologyID, report: List[OWLProfileReport]) {
    def successful = Reports(ontId,report.filter(_.isInProfile))
}

case class Report(ontId: OWLOntologyID, report: OWLProfileReport)

class OManager(man: OWLOntologyManager=OWLManager.createOWLOntologyManager()) {

  def fromFiles(files: List[Path]) = {
      files.foreach(f=>man.loadOntologyFromOntologyDocument(f.toIO))
//      man.importsClosure()
  }

  def load(url: URL) = man.loadOntology(IRI.create(url))
  /**
   * check ontology against each profile
   */
  def checkProfiles(o: OWLOntology) =
    Reports(o.getOntologyID,(for {p <- Profiles.values} yield p.checkOntology(o)).toList)

  def importsClosureReport(ont: OWLOntology): Seq[Reports]  = {
    val icl = ont.importsClosure().toScala(List)
    icl.map(o => checkProfiles(o))
  }
  
  import scala.collection.mutable.{HashMap => mHashMap, MultiMap => mMMap, Set => mSet}
  type MMap[X] = mHashMap[String, mSet[X]] with mMMap[String, X]

  def toMultiMap(reports: Seq[Reports]): MMap[Report]  = {
    val prid = reports.flatMap{ case Reports(id, repl) => repl.map(rep=> (rep.getProfile.getName, Report(id,rep))) }
    val mm = new mHashMap[String, mSet[Report]] with mMMap[String, Report]
    prid.foreach{ case (pr,id) => mm.addBinding(pr,id) }
    mm
  }

  def goodReports(mm: MMap[Report]) = mm.map{ case (profile,reports) =>
      (profile,
      reports.filter(r => r.report.isInProfile)
      )
    }
}

//@doc("find what OWL profile an ontology is respecting")
//@main
//def goodProfile(owlDoc: URL) = Ontology(owlDoc).checkProfiles.filter(_.isInProfile)

//@doc("return explanations to stdout for all the problematic profiles")
//@main
//def badProfile(owlDoc: URL): Unit = {
//  Ontology(owlDoc).checkProfiles.filterNot(_.isInProfile).foreach{ p =>
//    println("Profile:"+p.getProfile)
//    println("=======")
//    println()
//    p.getViolations.asScala.foreach(println(_))
//    println()
//    println()
//  }
//}

//@main
//def badProfileGit(extension: String, dir: os.Path = os.pwd) = {
//     ls.rec! dir |? (_.ext == extension)
//}



