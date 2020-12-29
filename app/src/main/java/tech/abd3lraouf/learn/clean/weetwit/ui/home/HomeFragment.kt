package tech.abd3lraouf.learn.clean.weetwit.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.rounded_edit_text.view.*
import tech.abd3lraouf.learn.clean.weetwit.R
import tech.abd3lraouf.learn.clean.weetwit.data.model.StatusModel
import tech.abd3lraouf.learn.clean.weetwit.util.DelayedTextWatcher

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupSwipeRefresh()
        subscribeToResults()
        subscribeToErrors()
        setUpTextWatcher()
    }

    /**
     * Subscribe to the search results live data.
     * On data update the recycler to show.
     */
    private fun subscribeToResults() {
        viewModel.searchResults.observe(viewLifecycleOwner, Observer { response ->
            swipeRefresh.isRefreshing = false
            if (response == null) {
                updateRecycler(arrayListOf());
                return@Observer
            }

            response.statusList.let { tweets -> updateRecycler(tweets) }
        })
    }

    /**
     * On error, display a snack bar
     */
    private fun subscribeToErrors() {
        viewModel.error.observe(
            viewLifecycleOwner,
            Observer { error ->
                Snackbar.make(tweetRecycler, error, Snackbar.LENGTH_LONG).show()
                swipeRefresh.isRefreshing = false
            })
    }

    /**
     * Initiate a text watcher on our search bar that executes a search after a delay
     */
    private fun setUpTextWatcher() {
        searchEditText.editText.addTextChangedListener(DelayedTextWatcher({ text ->
            viewModel.search(text)
        }))
    }

    /**
     * Initiate the swipe to refresh listener to grab new results for
     * current search on swipe down.
     */
    private fun setupSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    /**
     * Initiate the recylcerview adapter, layout manager, and engage the scroll listener
     */
    private fun setupRecycler() {
        tweetRecycler.adapter = TweetRecyclerAdapter(arrayListOf())
        tweetRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        setupScrollListener()
    }

    /**
     * Initiate a scroll listener to fetch more tweets when user reaches the end of results.
     */
    private fun setupScrollListener() {
        tweetRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager: LinearLayoutManager =
                    tweetRecycler.layoutManager as LinearLayoutManager
                val adapter: TweetRecyclerAdapter = tweetRecycler.adapter as TweetRecyclerAdapter

                if (layoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                    viewModel.next()
                }
            }
        })
    }

    /**
     * Update the recycler with the given data
     * @param tweets the tweet data to refresh the recycler with.
     */
    private fun updateRecycler(tweets: List<StatusModel>) {
        (tweetRecycler.adapter as TweetRecyclerAdapter).updateTweets(tweets)
    }
}