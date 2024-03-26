package ir.hoseinahmadi.myshop.Db.Fave

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.hoseinahmadi.myshop.component.Constants.Fave_TABLE

@Entity(tableName = Fave_TABLE)
data class FaveItem(
    @PrimaryKey
    val id:String,
    val title:String,
    val price:Double,
    val img:String,
)
