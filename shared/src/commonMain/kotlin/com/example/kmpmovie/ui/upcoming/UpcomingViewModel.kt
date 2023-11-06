package ui.upcoming

import com.example.kmpmovie.data.repository.MovieRepository
import com.example.kmpmovie.utils.network.DataState
import data.model.MovieItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel

class UpcomingViewModel : ViewModel() {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    private val repo = MovieRepository()
    val upComingMovieResponse = MutableStateFlow<DataState<List<MovieItem>>?>(DataState.Loading)

    fun nowPlayingView(page: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            repo.upComingMovie(page).collectLatest {
                upComingMovieResponse.value = it
            }
        }
    }
}