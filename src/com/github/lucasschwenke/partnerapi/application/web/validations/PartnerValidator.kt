package com.github.lucasschwenke.partnerapi.application.web.validations

import com.github.lucasschwenke.partnerapi.application.web.extensions.isInvalidCNPJ
import com.github.lucasschwenke.partnerapi.application.web.extensions.isNull
import com.github.lucasschwenke.partnerapi.application.web.extensions.isNullOrBlank
import com.github.lucasschwenke.partnerapi.application.web.requests.PartnerRequest

class PartnerValidator : Validator {

    override fun validate(partnerRequest: PartnerRequest): Map<String, List<String>> {
        val errorList = mutableListOf<Validation<*>>()

        errorList.add(Validation("trading_name", partnerRequest.tradingName).isNullOrBlank())
        errorList.add(Validation("owner_name", partnerRequest.ownerName).isNullOrBlank())
        errorList.add(Validation("document", partnerRequest.document).isNullOrBlank())
        errorList.add(Validation("document", partnerRequest.document).isInvalidCNPJ())
        errorList.add(Validation("coverage_area", partnerRequest.document).isNull())
        errorList.add(Validation("address", partnerRequest.address).isNull())

        return errorList.filter { it.errorMessageList.isNotEmpty() }
            .groupBy { it.fieldName }
            .mapValues { it.value.flatMap { valueItem -> valueItem.errorMessageList } }
    }
}
