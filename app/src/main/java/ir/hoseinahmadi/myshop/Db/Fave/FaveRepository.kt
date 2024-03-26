package ir.hoseinahmadi.myshop.Db.Fave

import androidx.room.Insert
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FaveRepository @Inject constructor(
    private val dao: FaveDao
) {

    val allFaveItem: Flow<List<FaveItem>> = dao.allFaveItem()

    fun insertFaveItem(item: FaveItem) = dao.insertItem(item)

    fun deleteItem(item: FaveItem) = dao.deleteItem(item)

    fun checkFaveItem(id:String):Flow<Boolean> =dao.isFaveItemExist(id)


}