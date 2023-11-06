package com.example.kmpmovie.ui.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmpmovie.utils.AppConstant
import com.example.kmpmovie.utils.AppString
import com.example.kmpmovie.utils.network.DataState
import ui.component.text.SubtitlePrimary
import ui.component.text.SubtitleSecondary
import com.seiko.imageloader.rememberImagePainter
import data.model.moviedetail.MovieDetail
import moe.tlaster.precompose.navigation.Navigator
import theme.DefaultBackgroundColor
import theme.FontColor
import ui.component.ProgressIndicator
import ui.component.shimmerBackground
import utils.hourMinutes
import utils.roundTo

@Composable
fun MovieDetail(
    navigator: Navigator,
    movieId: Int,
    movieDetailViewModel: MovieDetailViewModel = MovieDetailViewModel()
) {
    LaunchedEffect(1) {
        movieDetailViewModel.movieDetail(movieId)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                DefaultBackgroundColor
            )
    ) {
        movieDetailViewModel.movieDetail.collectAsState().value?.let {
            when (it) {
                is DataState.Loading -> {
                    ProgressIndicator()
                }

                is DataState.Success<MovieDetail> -> {
                    UiDetail(it.data)
                }
                is DataState.Error -> {
                    Text("Error :${it.exception}")
                }
            }
        }
    }
}
    @Composable
    fun UiDetail(data: MovieDetail){
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
             Image(
                 painter = rememberImagePainter(
                     AppConstant.IMAGE_URL.plus(
                         data.poster_path
                     )
                 ),
                 contentDescription = data.poster_path,
                 modifier = Modifier
                     .fillMaxWidth()
                     .height(300.dp).shimmerBackground(
                         RoundedCornerShape(5.dp)
                     ),
                 contentScale = ContentScale.Crop,
             )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                Text(
                    text = data.title,
                    modifier = Modifier.padding(top = 10.dp),
                    color = FontColor,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.W700,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp, top = 10.dp)
                ) {

                    Column(Modifier.weight(1f)) {
                        SubtitlePrimary(
                            text = data.original_language,
                        )
                        SubtitleSecondary(
                            text = AppString.LANGUAGE
                        )
                    }
                    Column(Modifier.weight(1f)) {
                        SubtitlePrimary(
                            text = data.vote_average.roundTo(1).toString(),
                        )
                        SubtitleSecondary(
                            text = AppString.RATING
                        )
                    }
                    Column(Modifier.weight(1f)) {
                        SubtitlePrimary(
                            text = data.runtime.hourMinutes()
                        )
                        SubtitleSecondary(
                            text = AppString.DURATION
                        )
                    }
                    Column(Modifier.weight(1f)) {
                        SubtitlePrimary(
                            text = data.release_date
                        )
                        SubtitleSecondary(
                            text = AppString.RELEASE_DATE
                        )
                    }
                }
                Text(
                    text = AppString.DESCRIPTION,
                    color = FontColor,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(text = data.overview)
            }
        }
    }


