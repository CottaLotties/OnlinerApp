package com.example.onlinerapp.ui.main.productdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.onlinerapp.autoCleared
import com.example.onlinerapp.databinding.ProductDetailFragmentBinding
import com.example.onlinerapp.entities.Product
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.product_detail_fragment.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private var binding: ProductDetailFragmentBinding by autoCleared()
    private val viewModel: ProductDetailViewModel by viewModels()
    private lateinit var product: Product

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = ProductDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("key")?.let { viewModel.start(it) }
        setupObservers()

        // Adding onClickListener for the addProductToCart button
        addToCart.setOnClickListener {
            addProductToCart()
        }
    }

    private fun setupObservers() {
        viewModel.product.observe(viewLifecycleOwner, {
            product = it
            bindProduct(it)
        })
    }

    private fun addProductToCart() = runBlocking {
        launch{
            viewModel.addToCart(product)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindProduct(product: Product) {
        binding.productName.text = product.name
        binding.productDescription.text = product.description
        if (product.prices?.offers?.count!=null)binding.productOffersCount.text = "Предложений: " + product.prices.offers.count.toString()
        else binding.productOffersCount.visibility = View.GONE
        if ((product.prices?.price_min?.amount != null)&&(product.prices.price_min.currency != null)) {
            binding.productPrice.text =
                "От " + product.prices.price_min.amount + " " + product.prices.price_min.currency
        } else binding.productPrice.visibility = View.GONE
        Glide.with(binding.root)
            .load("https:" + product.images.header)
            .into(binding.productImage)
    }
}