package ir.hoseinahmadi.myshop.Db.Fave

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FaveViewModel @Inject constructor(
    private val repository: FaveRepository
) : ViewModel() {

    val allFaveItem :Flow<List<FaveItem>> =repository.allFaveItem
    val faveCount :Flow<Int> =repository.faveCount

    fun addNewFaveItem(item: FaveItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFaveItem(item)
        }
    }


    fun deleteFaveItem(item: FaveItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(item)
        }
    }

    fun checkFaveItem(id:String):Flow<Boolean> =repository.checkFaveItem(id)


}