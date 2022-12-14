package dev.hossam.tawseelcompany.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.FragmentScoped
import dev.hossam.tawseelcompany.feature_order.presentation.orders.OrdersFilterAdapter

@Module
@InstallIn(FragmentComponent::class)
object AdapterModule {


//    @Provides
//    @FragmentScoped
//    fun provideOrdersFilterAdapter(@ApplicationContext context: Context): OrdersFilterAdapter {
//        return OrdersFilterAdapter(context)
//    }
}