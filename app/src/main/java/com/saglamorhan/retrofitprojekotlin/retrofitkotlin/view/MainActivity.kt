package com.saglamorhan.retrofitprojekotlin.retrofitkotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saglamorhan.retrofitprojekotlin.retrofitkotlin.R
import com.saglamorhan.retrofitprojekotlin.retrofitkotlin.view.model.CryptoModel
import com.saglamorhan.retrofitprojekotlin.retrofitkotlin.view.service.CryptoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModels : ArrayList<CryptoModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()
    }
    private fun loadData(){

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CryptoAPI::class.java)
        val call = service.getData()

        call.enqueue(object  : Callback<List<CryptoModel>> {
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                if (response.isSuccessful){

                    //.let egerki null ya da bos degilse bu blogu calistir anlamina gelir
                    response.body()?.let {

                        cryptoModels = ArrayList(it)

                        for(cryptoModel : CryptoModel in cryptoModels!! ){
                            println(cryptoModel.currency)
                            println(cryptoModel.price)
                        }

                    }
                }
            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }


        })

    }

}