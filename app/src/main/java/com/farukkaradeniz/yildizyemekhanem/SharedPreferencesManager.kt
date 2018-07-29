package com.farukkaradeniz.yildizyemekhanem

import android.content.SharedPreferences
import com.farukkaradeniz.yildizyemekhanem.model.YemekMenusu

/**
 * Created by Faruk Karadeniz on 22.07.2018.
 * Twitter: twitter.com/Omeerfk
 * Github: github.com/FarukKaradeniz
 * LinkedIn: linkedin.com/in/FarukKaradeniz
 * Website: farukkaradeniz.com
 */
class SharedPreferencesManager(val pref: SharedPreferences) {
    val editor: SharedPreferences.Editor = pref.edit()

    fun saveYemekMenuList(day: Int, month: Int, year: Int, menuList: List<YemekMenusu>) {
        menuList.forEach { yemekMenusu ->
            saveYemekMenu(day, month, year, yemekMenusu)
        }
    }

    fun saveYemekMenu(day: Int, month: Int, year: Int, menu: YemekMenusu) {
        when (menu) {
            is YemekMenusu.Ogrenci -> saveOgrenciMenu(day, month, year, menu)
            is YemekMenusu.Alakart -> saveAlakartMenu(day, month, year, menu)
        }
    }

    private fun saveOgrenciMenu(day: Int, month: Int, year: Int, menu: YemekMenusu.Ogrenci) {
        with(editor) {
            putString("O#$day#$month#$year#OM", menu.ogle)
            putString("O#$day#$month#$year#AM", menu.aksam)
            apply()
        }
    }

    private fun saveAlakartMenu(day: Int, month: Int, year: Int, menu: YemekMenusu.Alakart) {
        with(editor) {
            putString("A#$day#$month#$year#OM", menu.ogle)
            putString("A#$day#$month#$year#OA", menu.ogleAlt)
            putString("A#$day#$month#$year#AM", menu.aksam)
            putString("A#$day#$month#$year#AA", menu.aksamAlt)
            apply()
        }
    }

    fun getYemekMenu(day: Int, month: Int, year: Int, tip: String): YemekMenusu {
        return when (tip) {
            "Ogrenci" -> getOgrenciMenu(day, month, year)
            else -> getAlakartMenu(day, month, year)
        }
    }

    private fun getOgrenciMenu(day: Int, month: Int, year: Int): YemekMenusu.Ogrenci {
        val ogleMain = pref.getString("O#$day#$month#$year#OM", "Ogle Yemek Menusu Bulunamadı")
        val aksamMain = pref.getString("O#$day#$month#$year#AM", "Aksam Yemek Menusu Bulunamadı")
        return YemekMenusu.Ogrenci(ogleMain, aksamMain)
    }

    private fun getAlakartMenu(day: Int, month: Int, year: Int): YemekMenusu.Alakart {
        val ogleMain = pref.getString("A#$day#$month#$year#OM", "Ogle Yemek Menusu Bulunamadı")
        val ogleAlt = pref.getString("A#$day#$month#$year#OA", "Ogle alternatif Yemek Menusu Bulunamadı")
        val aksamMain = pref.getString("A#$day#$month#$year#AM", "Aksam Yemek Menusu Bulunamadı")
        val aksamAlt = pref.getString("A#$day#$month#$year#AA", "Aksam alternatif Yemek Menusu Bulunamadı")
        return YemekMenusu.Alakart(ogleMain, ogleAlt, aksamMain, aksamAlt)
    }
}