package com.noxinfinity.pdate.utils.helper

import com.apollographql.apollo.api.Optional

class ApolloHelper{
    companion object{
        fun <T> getOptionalParam(param: T?) : Optional<T> {
            return if(param == null ) return Optional.absent() else Optional.present(param)
        }
    }
}