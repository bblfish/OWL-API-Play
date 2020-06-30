import $ivy.`net.sourceforge.owlapi:owlapi-distribution:4.5.16`
import $ivy.`org.slf4j:slf4j-jdk14:1.7.29`

import org.semanticweb.owlapi.model.OWLOntology

import scala.collection.mutable
import scala.jdk.CollectionConverters._
import org.semanticweb.owlapi.profiles._
import org.semanticweb.owlapi.util._
import org.semanticweb.owlapi.model.parameters.Imports
import org.semanticweb.owlapi.model._
import org.semanticweb.owlapi.apibinding.OWLManager
import java.io.File


//to run with debuggin enabled run
//JAVA_OPTS="-Xmx4g -Djava.util.logging.config.file=$HOME/java.logging" amm findErrors.sc
//
// such a $HOME/java.logging properties file could contain
//handlers=java.util.logging.FileHandler
//java.util.logging.FileHandler.pattern=/Users/hjs/java.log
//java.util.logging.FileHandler.level = FINEST
//sun.net.www.protocol.http.HttpURLConnection.level=ALL

import os._


/** @param file: the file in a local repo from which others can be loaded **/
def loadGit(file: File ): OWLOntology = {
  val iMap = new AutoIRIMapper(file.getParentFile, true)
  val man = OWLManager.createOWLOntologyManager()
  man.getIRIMappers.add(iMap)
  iMap.update()
  println(s"loading ontology from $file")
  man.loadOntologyFromOntologyDocument(file)
}

def verify(ont: OWLOntology, profile: Profiles): mutable.Seq[OWLProfileViolation] =
  profile.checkOntology(ont).getViolations.asScala

@doc("load ontology and dependencies and test for DL and RL profiles")
@main
def main(ontRoot: java.io.File @doc("the ontology to load"),
         pre: String @doc("the prefix for error files written") = "errors"
        ) = {
  val ont = loadGit(ontRoot)
  import Profiles._
  def err(prof: Profiles) = temp(dir =pwd,prefix=s"$pre.",suffix=s".${prof}.txt",deleteOnExit = false)
  List(OWL2_DL,OWL2_RL).foreach { prof =>
    println(s"checking for $prof compliance")
    val x = prof.checkOntology(ont)
    val errorFile = err(prof)
    println(s"writing output to $errorFile")
    write.over(errorFile,
      x.getViolations.asScala.mkString(
        s"Errors for ${prof.getName} in ${ontRoot.getParent}\n"+
        s"Is In Profile: ${x.isInProfile}\n"
        , "\n#--> ", "\n")
    )
  }
}

