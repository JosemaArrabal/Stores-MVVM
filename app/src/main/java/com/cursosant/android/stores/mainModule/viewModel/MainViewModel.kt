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
    private var interactor: MainInteractor
    private var storeList: MutableList<StoreEntity>

    init {
        interactor = MainInteractor()
        storeList = mutableListOf()
    }

    private val stores: MutableLiveData<List<StoreEntity>> by lazy {

        // also es una variable de alcance que significa y además de
        MutableLiveData<List<StoreEntity>>().also {
            loadStores()
        }
    }

    fun getStores(): LiveData<List<StoreEntity>> {
        return stores
    }

    private fun loadStores() {
        interactor.getStores {
            stores.value = it
            storeList = it
        }
    }

    fun deleteStore(storeEntity: StoreEntity) {
        interactor.deleteStore(storeEntity, {
            val index = storeList.indexOf(storeEntity)
            if (index != -1) {
                storeList.removeAt(index)
                stores.value = storeList
            }

        })
    }

    fun updateStore(storeEntity: StoreEntity) {
        storeEntity.isFavorite = !storeEntity.isFavorite
        interactor.updateStore(storeEntity, {
            val index = storeList.indexOf(storeEntity)
            if (index != -1) {
                storeList.set(index, storeEntity)
                stores.value = storeList
            }

        })
    }
}