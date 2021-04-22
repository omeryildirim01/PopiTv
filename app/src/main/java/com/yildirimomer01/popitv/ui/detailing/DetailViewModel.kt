package com.yildirimomer01.popitv.ui.detailing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.yildirimomer01.popitv.model.Resource
import com.yildirimomer01.popitv.model.TvShowDetail
import com.yildirimomer01.popitv.repository.TvShowRepository
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
    val tvShow: LiveData<Resource<TvShowDetail>>
        get() = _tvShow
    init {

        if (handle.contains("tvShowId")){
            val movieId =   handle.get<Long>("tvShowId")
            compositeDisposable.addAll(
                tvShowRepository.getTvShowDetails(movieId!!)
                    .doOnSubscribe { _tvShow.value = Resource.Loading() }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {tv->
                            Timber.i(tv.toString())
                            _tvShow.value = Resource.Success(tv)
                        },
                        { error->
                            Timber.i(error.toString())
                            _tvShow.value = Resource.Error(error.message!!)
                        }
                    )
            )
        }else{
            _tvShow.value = Resource.Error("Sorry, tv show not found !")
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}