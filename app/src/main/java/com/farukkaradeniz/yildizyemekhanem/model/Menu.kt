package com.farukkaradeniz.yildizyemekhanem.model

/**
 * Created by Faruk Karadeniz on 22.07.2018.
 * Twitter: twitter.com/Omeerfk
 * Github: github.com/FarukKaradeniz
 * LinkedIn: linkedin.com/in/FarukKaradeniz
 * Website: farukkaradeniz.com
 */
sealed class YemekMenusu {
    data class Ogrenci(val ogle: String, val aksam: String) : YemekMenusu() {
        fun isEmpty(): Boolean {
            return ogle == "Ogle Yemek Menusu Bulunamadı"
                    && aksam == "Aksam Yemek Menusu Bulunamadı"
        }
    }

    data class Alakart(val ogle: String, val ogleAlt: String, val aksam: String, val aksamAlt: String) : YemekMenusu() {
        fun isEmpty(): Boolean {
            return ogle == "Ogle Yemek Menusu Bulunamadı"
                    && aksam == "Aksam Yemek Menusu Bulunamadı"
                    && ogleAlt == "Alternatif Menu Bulunamadı."
                    && aksamAlt == "Alternatif Menu Bulunamadı."
        }
    }
}
