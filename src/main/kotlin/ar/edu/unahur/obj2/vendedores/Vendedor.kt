package ar.edu.unahur.obj2.vendedores

class Certificacion(val esDeProducto: Boolean, val puntaje: Int)

abstract class Vendedor {
  // Acá es obligatorio poner el tipo de la lista, porque como está vacía no lo puede inferir.
  // Además, a una MutableList se le pueden agregar elementos
  val certificaciones = mutableListOf<Certificacion>()

  // Definimos el método abstracto.
  // Como no vamos a implementarlo acá, es necesario explicitar qué devuelve.
  abstract fun puedeTrabajarEn(ciudad: Ciudad): Boolean

  // En las funciones declaradas con = no es necesario explicitar el tipo
  fun esVersatil() =
    certificaciones.size >= 3
      && this.certificacionesDeProducto() >= 1
      && this.otrasCertificaciones() >= 1

  // Si el tipo no está declarado y la función no devuelve nada, se asume Unit (es decir, vacío)
  fun agregarCertificacion(certificacion: Certificacion) {
    certificaciones.add(certificacion)
  }

  fun esFirme() = this.puntajeCertificaciones() >= 30

  fun certificacionesDeProducto() = certificaciones.count { it.esDeProducto }
  fun otrasCertificaciones() = certificaciones.count { !it.esDeProducto }

  fun puntajeCertificaciones() = certificaciones.sumBy { c -> c.puntaje }

  /*puntos*/
  abstract fun esInfluyente(): Boolean

}

// En los parámetros, es obligatorio poner el tipo
class VendedorFijo(val ciudadOrigen: Ciudad) : Vendedor() {
  override fun puedeTrabajarEn(ciudad: Ciudad): Boolean {
    return ciudad == ciudadOrigen
  }

  override fun esInfluyente(): Boolean {
    return false
  }
}

// A este tipo de List no se le pueden agregar elementos una vez definida
class Viajante(val provinciasHabilitadas: List<Provincia>) : Vendedor() {
  override fun puedeTrabajarEn(ciudad: Ciudad): Boolean {
    return provinciasHabilitadas.contains(ciudad.provincia)
  }
  override fun esInfluyente():Boolean {
    return provinciasHabilitadas.sumBy { p -> p.poblacion } >= 10000000
  }
}

class ComercioCorresponsal(val ciudades: List<Ciudad>) : Vendedor() {

  override fun puedeTrabajarEn(ciudad: Ciudad): Boolean {
    return ciudades.contains(ciudad)
  }
  fun cantidadCiudadesConSucursal() : Int { return ciudades.size }
  fun provinciasConSucursal() : Set<Provincia> { return ciudades.map { c -> c.provincia }.toSet()}
  fun cantidadProvinciasConSucursal() : Int { return this.provinciasConSucursal().size}

  override fun esInfluyente():Boolean {
    return cantidadCiudadesConSucursal() >= 5 || cantidadProvinciasConSucursal() >= 3
  }
}

class CentroDeDistribucion (val ubicacion: Ciudad){
  var vendedores = mutableListOf<Vendedor>()

  fun hayVendedor(vendedor:Vendedor): Boolean {
    return this.vendedores.contains(vendedor)
  }

  fun agregarVendedor(vendedor: Vendedor) {
    if (!hayVendedor(vendedor)) {
      vendedores.add(vendedor)
    }
    else {
      throw error("El vendedor ya está registrado")
    }

  }
}