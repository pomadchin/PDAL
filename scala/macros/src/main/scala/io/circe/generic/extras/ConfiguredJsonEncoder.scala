package io.circe.generic.extras

import io.circe.generic.util.macros.JsonEncoderMacros
import macrocompat.bundle

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

class ConfiguredJsonEncoder extends scala.annotation.StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro ConfiguredJsonEncoderMacros.jsonCodecAnnotationMacro
}

@bundle
private[extras] class ConfiguredJsonEncoderMacros(val c: blackbox.Context) extends JsonEncoderMacros {
  import c.universe._

  protected[this] def semiautoObj: Symbol = symbolOf[semiauto.type].asClass.module

  def jsonCodecAnnotationMacro(annottees: Tree*): Tree = constructJsonCodec(annottees: _*)
}


