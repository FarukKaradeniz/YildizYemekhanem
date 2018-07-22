package com.farukkaradeniz.yildizyemekhanem.model

/**
 * Created by Faruk Karadeniz on 22.07.2018.
 * Twitter: twitter.com/Omeerfk
 * Github: github.com/FarukKaradeniz
 * LinkedIn: linkedin.com/in/FarukKaradeniz
 * Website: farukkaradeniz.com
 */
sealed class YemekMenusu {
    class Ogrenci(val ogle: String, val aksam: String) : YemekMenusu()
    class Alakart(val ogle: String, val ogleAlt: String, val aksam: String, val aksamAlt: String) : YemekMenusu()
}
