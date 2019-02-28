package io.horizontalsystems.ethereumkit.light.net.les.messages

import io.horizontalsystems.ethereumkit.light.models.BlockHeader
import io.horizontalsystems.ethereumkit.light.net.ILESMessage
import io.horizontalsystems.ethereumkit.light.rlp.RLP
import io.horizontalsystems.ethereumkit.light.rlp.RLPList
import io.horizontalsystems.ethereumkit.light.toLong

class BlockHeadersMessage(payload: ByteArray) : ILESMessage {

    companion object {
        const val code = 0x03
    }

    var requestID: Long = 0
    var bv: Long = 0
    var headers: MutableList<BlockHeader> = mutableListOf()

    init {
        val paramsList = RLP.decode2(payload)[0] as RLPList
        this.requestID = paramsList[0].rlpData.toLong()
        this.bv = paramsList[1].rlpData.toLong()
        val payloadList = paramsList[2] as RLPList
        for (i in 0 until payloadList.size) {
            val rlpData = payloadList[i] as RLPList
            headers.add(BlockHeader(rlpData))
        }
    }

    override var code: Int = Companion.code

    override fun encoded(): ByteArray {
        return byteArrayOf()
    }

    override fun toString(): String {
        return "Headers [requestId: $requestID; bv: $bv; headers (${headers.size}): [${headers.joinToString(separator = ", ")}}] ]"
    }
}