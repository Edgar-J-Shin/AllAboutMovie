package com.dcs.data.remote.network.serializer

import com.dcs.data.remote.model.RemoteGender
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object GenderSerializer : KSerializer<RemoteGender> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(RemoteGender::class.java.simpleName, PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: RemoteGender) {
        val intValue = RemoteGender.entries.indexOf(value)
        encoder.encodeInt(intValue)
    }

    override fun deserialize(decoder: Decoder): RemoteGender {
        return when (val intValue = decoder.decodeInt()) {
            0 -> RemoteGender.NOT_SPECIFIED
            1 -> RemoteGender.FEMALE
            2 -> RemoteGender.MALE
            3 -> RemoteGender.NON_BINARY
            else -> throw IllegalArgumentException("Invalid value for Gender: $intValue")
        }
    }
}
