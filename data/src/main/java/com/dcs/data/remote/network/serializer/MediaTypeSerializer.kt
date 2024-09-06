package com.dcs.data.remote.network.serializer

import com.dcs.data.remote.model.RemoteMediaType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object MediaTypeSerializer : KSerializer<RemoteMediaType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("MediaType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: RemoteMediaType) {
        encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): RemoteMediaType {
        return when (val stringValue = decoder.decodeString()) {
            RemoteMediaType.MOVIE.value -> RemoteMediaType.MOVIE
            RemoteMediaType.TV_SHOW.value -> RemoteMediaType.TV_SHOW
            else -> throw IllegalArgumentException("Invalid value for MediaType: $stringValue")
        }
    }
}
