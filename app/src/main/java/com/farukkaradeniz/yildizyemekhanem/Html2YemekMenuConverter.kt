package com.farukkaradeniz.yildizyemekhanem

import com.farukkaradeniz.yildizyemekhanem.model.YemekMenusu
import org.jsoup.Jsoup

/**
 * Created by Faruk Karadeniz on 22.07.2018.
 * Twitter: twitter.com/Omeerfk
 * Github: github.com/FarukKaradeniz
 * LinkedIn: linkedin.com/in/FarukKaradeniz
 * Website: farukkaradeniz.com
 */
class Html2YemekMenuConverter {
    companion object {
        //Daha güzel tasarlanmış bir çözüm bulunacak (ikinci parametreyi kastediyorum)
        fun getYemekhaneMenu(response: String, tip: String): List<Pair<String, YemekMenusu>> { //listof ögrencimenu
            val yemekMenusu = mutableListOf<Pair<String, YemekMenusu>>()
            val document = Jsoup.parse(response)
            val elements = document.select("#menu_background")
            elements.forEach { e ->
                val tarih = e.getElementById("menuFooterFilter").text()
                val lunchMain = e.getElementsByClass("one_lunchMainMenu").text()
                val lunchAlt = e.getElementsByClass("one_lunchAltMenu").text()
                val dinnerMain = e.getElementsByClass("one_dinnerMainMenu").text()
                val dinnerAlt = e.getElementsByClass("one_dinnerAltMenu").text()
                val yemekMenu: YemekMenusu = when (tip) {
                    "Ogrenci" -> YemekMenusu.Ogrenci(lunchMain, dinnerMain)
                    else -> YemekMenusu.Alakart(lunchMain, lunchAlt, dinnerMain, dinnerAlt)
                }
                yemekMenusu.add(Pair(tarih, yemekMenu))
            }
            return yemekMenusu
        }
    }
}