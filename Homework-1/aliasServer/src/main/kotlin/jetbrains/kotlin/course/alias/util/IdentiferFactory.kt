package jetbrains.kotlin.course.alias.util

import java.io.File
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

class IdentifierFactory {

    private var counter = 0
    private val objectMapper = jacksonObjectMapper()

    fun uniqueIdentifier(): Identifier {
        return counter++
    }

    fun getLastUsedId(): Int = counter

    fun setCounter(lastUsedId: Int) {
        this.counter = lastUsedId
    }

    fun saveLastUsedId() {
        val file = File("last_used_id.json")
        file.writeText(objectMapper.writeValueAsString(counter))
    }

    fun loadLastUsedId() {
        val file = File("last_used_id.json")
        if (file.exists()) {
            val id = file.readText()
            counter = objectMapper.readValue(id)
        }
    }
}
