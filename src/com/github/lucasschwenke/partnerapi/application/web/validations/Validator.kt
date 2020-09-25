package com.github.lucasschwenke.partnerapi.application.web.validations

import com.github.lucasschwenke.partnerapi.application.web.requests.PartnerRequest

interface Validator {

    fun validate(partnerRequest: PartnerRequest): Map<String, List<String>>
}