package ps.project.mapping.xml.lattes

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule

class EmptyStringAsNullModule : SimpleModule() {
    init {
        addDeserializer(String::class.java, EmptyStringAsNullDeserializer())
    }
}

class EmptyStringAsNullDeserializer :
    StdDeserializer<String>(String::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): String? {
        val value = p.valueAsString
        return if (value.isNullOrBlank()) null else value
    }
}

