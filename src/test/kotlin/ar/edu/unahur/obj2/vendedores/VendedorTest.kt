package ar.edu.unahur.obj2.vendedores

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain

class ComercioTest : DescribeSpec({
  val buenosAires = Provincia(15000000)
  val santaFe = Provincia(9000000)
  val cordoba = Provincia(12000000)
  val entreRios = Provincia(1500000)

  val chivilcoy = Ciudad(buenosAires)
  val bragado = Ciudad(buenosAires)
  val lobos = Ciudad(buenosAires)
  val pergamino = Ciudad(buenosAires)
  val zarate = Ciudad(buenosAires)
  val rosario = Ciudad(santaFe)
  val rafaela = Ciudad(santaFe)
  val sanFrancisco = Ciudad(cordoba)
  val diamante = Ciudad(entreRios)
  val armstrong = Ciudad(santaFe)

  describe("Es influyente1") {
    val corresponsal = ComercioCorresponsal(listOf(chivilcoy, bragado, lobos, pergamino, zarate))
    it("5 ciudades") {
      corresponsal.esInfluyente().shouldBeTrue()
    }
  }
  describe("Es influyente2") {
    val corresponsal2 = ComercioCorresponsal(listOf(rosario, rafaela, sanFrancisco, diamante))
    it("3 provincias") {
      corresponsal2.esInfluyente().shouldBeTrue()
    }
  }
  describe("Es influyente3") {
    val corresponsal3 = ComercioCorresponsal(listOf(rosario, rafaela, armstrong, diamante))
    it("4 ciudades, 2 pcias") {
      corresponsal3.esInfluyente().shouldBeFalse()
    }
  }


})
class VendedorTest : DescribeSpec({
  val misiones = Provincia(1300000)
  val sanIgnacio = Ciudad(misiones)

  describe("Vendedor fijo") {
    val obera = Ciudad(misiones)
    val vendedorFijo = VendedorFijo(obera)

    describe("puedeTrabajarEn") {
      it("su ciudad de origen") {
        vendedorFijo.puedeTrabajarEn(obera).shouldBeTrue()
      }
      it("otra ciudad") {
        vendedorFijo.puedeTrabajarEn(sanIgnacio).shouldBeFalse()
      }
    }
  }

  describe("Viajante") {
    val cordoba = Provincia(2000000)
    val villaDolores = Ciudad(cordoba)
    val viajante = Viajante(listOf(misiones))

    describe("puedeTrabajarEn") {
      it("una ciudad que pertenece a una provincia habilitada") {
        viajante.puedeTrabajarEn(sanIgnacio).shouldBeTrue()
      }
      it("una ciudad que no pertenece a una provincia habilitada") {
        viajante.puedeTrabajarEn(villaDolores).shouldBeFalse()
      }
    }
  }
})

class CentroDistribucionTest : DescribeSpec({
  val buenosAires = Provincia(15000000)
  val chivilcoy = Ciudad(buenosAires)
  val centro1 = CentroDeDistribucion(chivilcoy)
  val valeria = VendedorFijo(chivilcoy)

  describe("Agregar vendedores") {
    it("Agregar un vendedor") {
      centro1.agregarVendedor(valeria)
      centro1.vendedores.shouldContain(valeria)
    }
    it("no permite agregar dos veces al mismo vendedor") {
      shouldThrowAny {
        centro1.agregarVendedor(valeria)
      }
    }
  }

  })

