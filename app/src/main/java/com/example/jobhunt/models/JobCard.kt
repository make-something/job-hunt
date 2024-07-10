package com.example.jobhunt.models

data class JobCard(
    var nama_perusahaan : String ?= null,
    var posisi : String ?= null,
    var lokasi : String ?= null,
    var keterampilan : String ?= null
)
