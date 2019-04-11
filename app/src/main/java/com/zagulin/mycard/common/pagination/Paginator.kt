package com.zagulin.mycard.common.pagination

import com.zagulin.mycard.models.PaginationData
import io.reactivex.Observable


interface Paginator<Entity> {
    /**
     * Оповещение о необходимости выполнения загрузки следующей партии элементов.
     */
    fun loadNextPage()

    /**
     * Оповещение о необходимости выполнения загрузки списка элементов заново.
     * загружает и отображает первую страницу! остальные данные удаляются из списка
     */
    fun reload()

    /**
     * Предоставляет доступ к [Observable], эмитящему результаты выполнения запросов.
     *
     * [PaginationData], которые эмитит пагинатор предоставляют полную информацию о состоянии пагинации.
     * В частности, поле [PaginationData.dataList] будет отправлять полный список загруженных элементов
     */
    fun paginationObservable(): Observable<PaginationData<Entity>>

}