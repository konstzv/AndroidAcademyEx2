package com.zagulin.mycard.ui.fragment

import com.zagulin.mycard.R


abstract class HolderFragment:BaseFragment(){

    abstract  val currentFragment: BaseFragment?


    override fun onBackPressed(){
        currentFragment?.let {
            if (it.childFragmentManager.backStackEntryCount == 0){
                childFragmentManager.popBackStack()
            }else{
                it.onBackPressed()
            }

        }?:    childFragmentManager.popBackStack()


//                currentFragment?.let {
//            if (it.childFragmentManager.backStackEntryCount == 1){
//                childFragmentManager.popBackStack()
//            }else{
//                it.onBackPressed()
//            }
//        }?:childFragmentManager.popBackStack()


//        currentFragment?.let {
//            if (it.childFragmentManager.backStackEntryCount == 1){
//                childFragmentManager.popBackStack()
//            }else{
//                it.childFragmentManager.popBackStack()
//            }
//        }?:childFragmentManager.popBackStack()

//        val currentFragmentManager =currentFragment?.childFragmentManager
//
//        currentFragmentManager?.let {
//            if (it.backStackEntryCount == 1){
//                childFragmentManager.popBackStack()
//            }else{
//                currentFragmentManager.popBackStack()
//            }
//        }?:
//        childFragmentManager.popBackStack()
    }
}