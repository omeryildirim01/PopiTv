package com.yildirimomer01.popitv.ui.detailing

import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.yildirimomer01.popitv.R
import com.yildirimomer01.popitv.model.Resource
import com.yildirimomer01.popitv.model.TvShowDetail
import com.yildirimomer01.popitv.module.GlideApp
import com.yildirimomer01.popitv.repository.TvShowRepository
import com.yildirimomer01.popitv.util.RatioImageView
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val tvShowRepository: TvShowRepository,
    val handle: SavedStateHandle,
) : ViewModel() {
    private val _tvShow = MutableLiveData<Resource<TvShowDetail>>()
    private val compositeDisposable = CompositeDisposable()
    private val errorMessage = "Sorry, tv show not found !"
    val tvShow: LiveData<Resource<TvShowDetail>>
        get() = _tvShow

    init {
        if (handle.contains("tvShowId")) {
            val tvId = handle.get<Long>("tvShowId")
            compositeDisposable.addAll(
                tvShowRepository.getTvShowDetails(tvId!!)
                    .doOnSubscribe { _tvShow.value = Resource.Loading() }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { tv ->
                            Timber.i(tv.toString())
                            _tvShow.value = Resource.Success(tv)
                        },
                        { error ->
                            Timber.i(error.toString())
                            _tvShow.value = Resource.Error(error.message!!)
                        }
                    )
            )
        } else {
            _tvShow.value = Resource.Error(errorMessage)
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    companion object {
        @JvmStatic
        @BindingAdapter("originalImageUrl")
        fun loadOriginalImage(view: RatioImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                GlideApp.with(view)
                    .load("https://image.tmdb.org/t/p/original${url}")
                    .placeholder(R.drawable.ic_image_placeholder)
                    .error(R.drawable.ic_error_image)
                    .into(view)
            }
        }

        @JvmStatic
        @BindingAdapter("posterImageUrl")
        fun loadPosterImage(view: RatioImageView, url: String?) {
            if (!url.isNullOrEmpty()) {
                GlideApp.with(view)
                    .load("https://image.tmdb.org/t/p/w500${url}")
                    .placeholder(R.drawable.ic_image_placeholder)
                    .error(R.drawable.ic_error_image)
                    .into(view)
            }
        }

        @JvmStatic
        @BindingAdapter("ratings")
        fun setRating(ratingBar: AppCompatRatingBar, rating: Double?) {
            try {
                if (rating != null) {
                    ratingBar.rating = (rating/2).toFloat()
                }
            } catch (ex: Throwable) {
                Timber.e(ex)
            }
        }
    }
}