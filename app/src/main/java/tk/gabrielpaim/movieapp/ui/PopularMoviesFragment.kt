package tk.gabrielpaim.movieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tk.gabrielpaim.movieapp.R
import tk.gabrielpaim.movieapp.databinding.MovieListItemBinding
import tk.gabrielpaim.movieapp.databinding.PopularMoviesFragmentBinding
import tk.gabrielpaim.movieapp.domain.Movie

class PopularMoviesFragment : Fragment() {
    private val viewModel: MainActivityViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }

        ViewModelProvider(this, MainActivityViewModel.Factory(activity.application)).get(MainActivityViewModel::class.java)
    }

    private var viewModelAdapter: PopularMoviesAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.popularMovies.observe(viewLifecycleOwner, Observer<List<Movie>> { movies ->
            movies?.apply {
                viewModelAdapter?.popularMovies = movies
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: PopularMoviesFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.popular_movies_fragment,
            container,
            false
        )

        binding.setLifecycleOwner(viewLifecycleOwner)

        binding.viewModel = viewModel

        viewModelAdapter = PopularMoviesAdapter()

        binding.root.findViewById<RecyclerView>(R.id.home_recyclerview).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        return binding.root
    }
}

class PopularMoviesAdapter : RecyclerView.Adapter<PopularMoviesViewHolder>() {
    var popularMovies: List<Movie> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        val withDataBinding: MovieListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            PopularMoviesViewHolder.LAYOUT,
            parent,
            false
        )
        return PopularMoviesViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.movie = popularMovies[position]
        }

    }

    override fun getItemCount() = popularMovies.size
}


class PopularMoviesViewHolder(val viewDataBinding: MovieListItemBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.movie_list_item
    }
}

