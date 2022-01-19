package com.cursosant.android.stores.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cursosant.android.stores.StoreApplication
import com.cursosant.android.stores.common.entities.StoreEntity
import com.cursosant.android.stores.mainModule.model.MainInteractor
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainViewModel : ViewModel() {

    // Esta propiedad sirve para reflejar los datos de la vista
    private var stores: MutableLiveData<List<StoreEntity>>
    private var interactor: MainInteractor

    init {
        interactor = MainInteractor()
        stores = MutableLiveData()
        loadStores()
    }

    fun getStores(): LiveData<List<StoreEntity>> {
        return stores
    }

    private fun loadStores() {
        interactor.getStoresCallback(object : MainInteractor.StoresCallback{
            override fun getStoresCallback(stores: MutableList<StoreEntity>) {
                this@MainViewModel.stores.value = stores

            }
        })
    }
}