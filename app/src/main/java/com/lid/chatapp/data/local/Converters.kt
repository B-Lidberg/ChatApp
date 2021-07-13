package com.lid.chatapp.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.lid.chatapp.data.model.Source

@ProvidedTypeConverter
class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}