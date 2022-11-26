package com.blessingdropshop.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blessingdropshop.app.adapters.constants.NODE_ITEMS
import com.blessingdropshop.app.models.AdminItemModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class ItemsViewModel: ViewModel() {
    private val dbItems = FirebaseDatabase.getInstance().getReference(NODE_ITEMS)

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?> get() = _result

    private val _items = MutableLiveData<AdminItemModel>()
    val items: LiveData<AdminItemModel> get() =_items

    fun addItem(item: AdminItemModel){
        item.id = dbItems.push().key
        dbItems.child(item.id!!).setValue(item).addOnCompleteListener {
            if(it.isSuccessful){
                _result.value = null
            }
            else{
                _result.value = it.exception
            }
        }
    }
    fun updateItem(item: AdminItemModel){
        dbItems.child(item.id!!).setValue(item)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    _result.value = null
                }else{
                    _result.value = it.exception
                }
            }
    }

    private val childEventListener = object: ChildEventListener{
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val items = snapshot.getValue(AdminItemModel::class.java)
            items?.id = snapshot.key
            _items.value = items!!
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            val items = snapshot.getValue(AdminItemModel::class.java)
            items?.id = snapshot.key
            _items.value = items!!
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            TODO("Not yet implemented")
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            TODO("Not yet implemented")
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    }
    fun getRealtimeUpdate(){
        dbItems.addChildEventListener(childEventListener)
    }

    override fun onCleared() {
        super.onCleared()
        dbItems.removeEventListener(childEventListener)
    }
}