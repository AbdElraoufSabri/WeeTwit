package tech.abd3lraouf.learn.clean.weetwit.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_tweet.view.*
import tech.abd3lraouf.learn.clean.weetwit.R
import tech.abd3lraouf.learn.clean.weetwit.data.model.StatusModel
import tech.abd3lraouf.learn.clean.weetwit.util.ImageSizes
import tech.abd3lraouf.learn.clean.weetwit.util.toSimpleDateString
import tech.abd3lraouf.learn.clean.weetwit.util.userImageToSized

class TweetRecyclerAdapter(private var tweets: List<StatusModel>) :
    RecyclerView.Adapter<TweetRecyclerAdapter.TweetViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        return TweetViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_tweet, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        holder.bind(tweets[position])
    }

    fun updateTweets(tweets: List<StatusModel>) {
        this.tweets = tweets
        notifyDataSetChanged()
    }

    class TweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tweet: StatusModel) {
            Glide.with(itemView)
                .load(tweet.user.profileImageUrl.userImageToSized(ImageSizes.SIZE_200))
                .apply(RequestOptions.circleCropTransform()).into(itemView.userImage)
            itemView.displayName.text = tweet.user.name
            itemView.userName.text =
                itemView.context.getString(R.string.screenName, tweet.user.screenName)
            itemView.tweet.text = tweet.text
            itemView.retweets.text = tweet.retweetCount.toString()
            itemView.likes.text = tweet.favoriteCount.toString()
            itemView.date.text = tweet.createdAt.toSimpleDateString()
        }
    }
}