package com.example.onlinerapp.ui.main.productdetail

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.onlinerapp.R
import com.example.onlinerapp.autoCleared
import com.example.onlinerapp.databinding.ProductDetailFragmentBinding
import com.example.onlinerapp.entities.product.Product
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private val categoryKeyTag = "key"
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
        arguments?.getString(categoryKeyTag)?.let { viewModel.start(it) }
        setupObservers()

        // Adding onClickListener for the addProductToCart button
            binding.addToCart.setOnClickListener {
            viewModel.addToCart(product)
                checkButton()
        }
    }

    private fun setupObservers() {
        viewModel.product.observe(viewLifecycleOwner, {
            product = it
            bindProduct(it)
        })
    }

    private fun bindProduct(product: Product) {
        binding.productName.text = product.name
        binding.productDescription.text = product.description
        if (product.prices?.offers?.count!=null)binding.productOffersCount.text = String.format(requireActivity().getString(R.string.product_offers_count),
            product.prices.offers.count.toString())
        else binding.productOffersCount.visibility = View.GONE
        if ((product.prices?.price_min?.amount != null)&&(product.prices.price_min.currency != null)) {
            binding.productPrice.text = String.format(requireActivity().getString(R.string.price_from_to),
                product.prices.price_min.amount.toString(), product.prices.price_min.currency)
        } else binding.productPrice.visibility = View.GONE
        Glide.with(binding.root)
            .load("https:" + product.images.header)
            .into(binding.productImage)
        checkButton()
    }

    private var inCart = false
    // function to change the fab button if needed
    private fun checkButton(){
        ifInCart()
            if(inCart) {
                binding.addToCart.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.teal_200))
                binding.addToCart.setImageResource(R.drawable.baseline_done_white_18)
                binding.addToCart.isEnabled = false
            }
    }

    // function to check if the product is in the cart
    private fun ifInCart() = runBlocking {
        launch {
            inCart = viewModel.inCart(product.key)
        }
    }
}