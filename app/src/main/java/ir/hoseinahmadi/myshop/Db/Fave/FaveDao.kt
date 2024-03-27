package ir.hoseinahmadi.myshop.Db.Fave

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FaveDao {

    @Query("SELECT * FROM Fave_TABLE")
    fun allFaveItem():Flow<List<FaveItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item:FaveItem)

    @Delete
    fun deleteItem(item: FaveItem)

    @Query("SELECT EXISTS(SELECT * FROM Fave_TABLE WHERE id =:itemId)")
    fun isFaveItemExist(itemId: String): Flow<Boolean>

    @Query("SELECT COUNT(*) FROM Fave_TABLE")
    fun getFaveCount(): Flow<Int>

}