package kr.applepi.ideastation.data

/**
 * Created by 최예찬 on 2016-09-24.
 */

data class IdeaReview(
        var reviewCustom: IdeaReviewCustom? = null,
        var reviewSWOT: IdeaReviewSWOT? = null,
        var review5why: IdeaReview5why? = null
)

data class IdeaReviewCustom(
        var popularity: String, var essentiality: String,
        var expendable: String, var convenience: String
)

data class IdeaReviewSWOT(
        var s: String, var w: String,
        var o: String, var t: String
)

data class IdeaReview5why(
        var problem: String,
        var w1: String, var w2: String, var w3: String, var w4: String, var w5: String,
        var solution: String
)
