package org.dbpedia.extraction.mappings.rml.model.template.assembler

import org.dbpedia.extraction.mappings.rml.model.RMLModel
import org.dbpedia.extraction.mappings.rml.model.resource.{RMLLiteral, RMLPredicateObjectMap, RMLTriplesMap, RMLUri}
import org.dbpedia.extraction.mappings.rml.model.template.SimplePropertyTemplate
import org.dbpedia.extraction.mappings.rml.model.template.assembler.TemplateAssembler.Counter

import scala.collection.JavaConverters._

/**
  * Created by wmaroy on 25.07.17.
  */
class ShortSimplePropertyTemplateAssembler(rmlModel : RMLModel, baseUri: String, language : String, template: SimplePropertyTemplate, counter : Counter) {

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //  Public methods
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  def assemble(independent : Boolean = false) : List[RMLPredicateObjectMap] = {
    if(!independent) {
      val triplesMap = rmlModel.triplesMap
      addSimplePropertyMappingToTriplesMap(baseUri, triplesMap)
    } else {
      addIndependentSimplePropertyMappingToTriplesMap(baseUri)
    }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //  Private methods
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private def addIndependentSimplePropertyMappingToTriplesMap(uri: String) : List[RMLPredicateObjectMap] = {

    val simplePropertyMappingUri = RMLUri(uri + "/SimplePropertyMapping/" + counter.simpleProperties)
    val simplePmPom = rmlModel.rmlFactory.createRMLPredicateObjectMap(simplePropertyMappingUri)

    simplePmPom.addPredicate(RMLUri(template.ontologyProperty.uri))

    val objectMapUri = simplePropertyMappingUri.extend("/ObjectMap")
    val objectMap = simplePmPom.addObjectMap(objectMapUri)

    val rmlReference = RMLLiteral(template.property)
    objectMap.addRMLReference(rmlReference)

    List(simplePmPom)

  }

  private def addSimplePropertyMappingToTriplesMap(uri: String, triplesMap: RMLTriplesMap) : List[RMLPredicateObjectMap] =
  {

    val simplePropertyMappingUri = RMLUri(uri + "/SimplePropertyMapping/" + counter.simpleProperties)
    val simplePmPom = triplesMap.addPredicateObjectMap(simplePropertyMappingUri)

    simplePmPom.addPredicate(RMLUri(template.ontologyProperty.uri))

    val objectMapUri = simplePropertyMappingUri.extend("/ObjectMap")
    val objectMap = simplePmPom.addObjectMap(objectMapUri)

    val rmlReference = RMLLiteral(template.property)
    objectMap.addRMLReference(rmlReference)

    List(simplePmPom)

  }

}
