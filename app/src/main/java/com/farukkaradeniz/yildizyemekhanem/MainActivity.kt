package com.farukkaradeniz.yildizyemekhanem

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.farukkaradeniz.yildizyemekhanem.api.YemekhaneAPI
import com.farukkaradeniz.yildizyemekhanem.api.YemekhaneService
import com.farukkaradeniz.yildizyemekhanem.model.YemekMenusu
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {
    private val calendar = Calendar.getInstance()
    private lateinit var preferencesManager: SharedPreferencesManager
    private lateinit var yemekhaneService: YemekhaneService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        preferencesManager = SharedPreferencesManager(sharedPreferences)
        yemekhaneService = YemekhaneAPI.getYemekhaneService()

        val todayOgrenciMenu = preferencesManager.getOgrenciMenu(getDay(), getMonth(), getYear())
        val todayAlakartMenu = preferencesManager.getAlakartMenu(getDay(), getMonth(), getYear())
        updateOgrenciUI(todayOgrenciMenu)
        switchButton.setOnSwitchListener { position, _ ->
            when (position) {
                0 -> updateOgrenciUI(todayOgrenciMenu)
                else -> updateAlakartUI(todayAlakartMenu)
            }
        }

        //Haftasonu olduğunda ekstra birşeyler konulabilir. Tekrar tekrar api çağrısı yapmaması için.
        if (todayAlakartMenu.isEmpty() || todayOgrenciMenu.isEmpty()) {
            downloadOgrenciMenu() //Default olarak UI ogrenciyi gosterecek
            downloadAlakartMenu()
        }

    }

    private fun downloadOgrenciMenu() {
        yemekhaneService.ogrenciMenu(getMonth(), getYear())
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        val ogrenciMenuList = Html2YemekMenuConverter
                                .getYemekhaneMenu(response?.body()?.string()!!, YemekMenusu.Ogrenci::class.java.simpleName)

                        ogrenciMenuList.forEach { pairMenu ->
                            saveData(pairMenu)
                            Log.i("Saved (${pairMenu.first}): ", pairMenu.second.toString())
                        }

                        val ogrenciMenu = preferencesManager.getOgrenciMenu(getDay(), getMonth(), getYear())
                        updateOgrenciUI(ogrenciMenu)
                    }

                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        Toast.makeText(this@MainActivity, "Error downloading data", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun downloadAlakartMenu() {
        yemekhaneService.alakartMenu(getMonth(), getYear())
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        val alakartMenuList = Html2YemekMenuConverter
                                .getYemekhaneMenu(response?.body()?.string()!!, YemekMenusu.Alakart::class.java.simpleName)
                        alakartMenuList.forEach { pairMenu ->
                            saveData(pairMenu)
                            Log.i("Saved (${pairMenu.first}): ", pairMenu.second.toString())
                        }
                        val alakartMenu = preferencesManager.getAlakartMenu(getDay(), getMonth(), getYear())
                        updateAlakartUI(alakartMenu)
                    }

                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        Toast.makeText(this@MainActivity, "Error downloading data", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun saveData(pair: Pair<String, YemekMenusu>) {
        val tarih = pair.first.split(".")
        preferencesManager.saveYemekMenu(
                tarih[0].toInt(),
                tarih[1].toInt(),
                tarih[2].toInt(),
                pair.second)
    }

    private fun updateOgrenciUI(menu: YemekMenusu.Ogrenci) {
        Log.i(javaClass.simpleName, "Ogrenci UI")
        ogleMenuIcerik.text = menu.ogle
        ogleAlternatifMenuIcerik.text = getString(R.string.alternatif_bulunamadi)
        aksamMenuIcerik.text = menu.aksam
        aksamAlternatifMenuIcerik.text = getString(R.string.alternatif_bulunamadi)
    }

    private fun updateAlakartUI(menu: YemekMenusu.Alakart) {
        Log.i(javaClass.simpleName, "Alakart UI")
        ogleMenuIcerik.text = menu.ogle
        ogleAlternatifMenuIcerik.text = menu.ogleAlt
        aksamMenuIcerik.text = menu.aksam
        aksamAlternatifMenuIcerik.text = menu.aksamAlt
    }

    private fun getYear(): Int = calendar.get(Calendar.YEAR)

    private fun getMonth(): Int = calendar.get(Calendar.MONTH) + 1

    private fun getDay(): Int = calendar.get(Calendar.DATE)

}
