package ex2

import ex2.Exercise2.Question.*

import scala.collection.immutable.HashSet

object Exercise2 :
  /**
   * For each article, the reviewer has to reply to all the following questions
   */
  object Question extends Enumeration :
    type Question = Value
    val RELEVANCE, // ("È importante per questa conferenza?"),
    SIGNIFICANCE, // ("Produce contributo scientifico?"),
    CONFIDENCE, // ("Ti senti competente a commentarlo?");
    FINAL = Value
    // ("É un articolo da accettare?")


  trait ConferenceReviewing :
    import Question.*
    /**
     * @param article
     * @param scores
     * loads a review for the specified article, with complete scores as a map
     */
    def loadReview(article: Int, scores: Map[Question, Int]): ConferenceReviewing

    /**
     * @param article
     * @param relevance
     * @param significance
     * @param confidence
     * @param fin
     * loads a review for the specified article, with the 4 explicit scores
     */
    def loadReview(article: Int, relevance: Int, significance: Int, confidence: Int, fin: Int): ConferenceReviewing

    /**
     * @param article
     * @param question
     * @return the scores given to the specified article and specified question, as an (ascending-ordered) reviews
     */
    def orderedScores(article: Int, question: Question): List[Int]

    /**
     * @param article
     * @return the average score to question FINAL taken by the specified article
     */
    def averageFinalScore(article: Int): Double

    /**
     * An article is considered accept if its averageFinalScore (not weighted) is > 5,
     * and at least one RELEVANCE score that is >= 8.
     *
     * @return the set of accepted articles
     */
    def acceptedArticles: HashSet[Int]

    /**
     * @return accepted articles as a reviews of pairs article+averageFinalScore, ordered from worst to best based on averageFinalScore
     */
    def sortedAcceptedArticles: List[(Int, Double)]

    /**
     * @return a map from articles to their average "weighted final score", namely,
     *         the average value of CONFIDENCE*FINAL/10
     *         Note: this method is optional in this exam
     */
    def averageWeightedFinalScoreMap: Map[Int, Double]


  private class ConferenceRewiewingImpl(private val reviews: List[(Int, Map[Question, Int])]) extends ConferenceReviewing :

    override def loadReview(article: Int, scores: Map[Question, Int]): ConferenceRewiewingImpl =
      ConferenceRewiewingImpl((article, scores) :: reviews)

    override def loadReview(article: Int, relevance: Int, significance: Int,
                            confidence: Int, fin: Int): ConferenceRewiewingImpl =
      val map: Map[Question, Int] = Map(RELEVANCE -> relevance,
        SIGNIFICANCE -> significance,
        CONFIDENCE -> confidence,
        FINAL -> fin)
      ConferenceRewiewingImpl((article, map) :: reviews)
    
    override def orderedScores(article: Int, question: Question): List[Int] = 
      reviews.filter(p => p._1 == article).map(p => p._2(question)).sorted()

    override def averageFinalScore(article: Int): Double = ???

    override def acceptedArticles: HashSet[Int] = ???

    override def sortedAcceptedArticles: List[(Int, Double)] = ???

    override def averageWeightedFinalScoreMap: Map[Int, Double] = ???

  object ConferenceReviewing:
    def apply(): ConferenceReviewing = new ConferenceRewiewingImpl(Nil)







