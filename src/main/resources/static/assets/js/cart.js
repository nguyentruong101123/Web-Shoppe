document.addEventListener("DOMContentLoaded", function() {
	loadCartItem();
});

function loadCartItem() {
    const cartItemsContainer = document.querySelector('.container-main');
    let cart = JSON.parse(localStorage.getItem('cart')) || []; // Lấy giỏ hàng từ localStorage

    if (cart.length === 0) {
        cartItemsContainer.innerHTML = '<p>Giỏ hàng của bạn trống.</p>';
        return;
    }

    cartItemsContainer.innerHTML = ''; // Xóa nội dung cũ
    let totalAmount = 0;

    // Lặp qua từng sản phẩm trong giỏ hàng và hiển thị chúng
    cart.forEach(product => {
        const shop = document.createElement("div");
        shop.classList.add('favorite-product');
        shop.innerHTML = `
            <input class="ip-checkbox" type="checkbox" name="sanpham" data-price="${product.price}" data-quantity="${product.quantity}">
            <div class="favorite">Yêu thích</div>
            <p>${product.shopName}</p>
        `;

        const itemDiv = document.createElement('div');
        itemDiv.classList.add('product-container');
        itemDiv.innerHTML = `
            <input class="ip-checkbox" type="checkbox" name="sanpham" data-price="${product.price}" data-quantity="${product.quantity}">
            <div class="img-product">
                <img width="80px" height="80px" src="${product.mainImage}" alt="">
                <p>${product.productDescription}</p>
            </div>  
            <p class="phanloai1">${product.colorName}</p>
            <p class="phanloai1">${product.sizeName}</p>
            <p class="dongia1"><u style="font-size: 10px; color: #abacab;">đ</u>${product.price}đ</p>
            <p class="soluong1">${product.quantity}</p>
            <p class="sotien1"><u style="font-size: 10px;">đ</u>${product.price * product.quantity}đ</p>
            <p class="thaotac1">
                <a href="#" onclick="removeFromCart(${product.productId})">Xóa</a><br>
            </p>
        `;

        cartItemsContainer.appendChild(shop);
        cartItemsContainer.appendChild(itemDiv);
    });

    // Thêm sự kiện cho các checkbox
    const checkboxes = document.querySelectorAll('.ip-checkbox');
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', updateTotal);
    });

    // Cập nhật tổng thanh toán
    updateTotal(); // Gọi hàm này để cập nhật tổng thanh toán ban đầu
}

// Hàm để cập nhật tổng thanh toán
function updateTotal() {
    const checkboxes = document.querySelectorAll('.ip-checkbox:checked');
    let totalAmount = 0;

    checkboxes.forEach(checkbox => {
        const price = parseFloat(checkbox.getAttribute('data-price'));
        const quantity = parseInt(checkbox.getAttribute('data-quantity'));
        totalAmount += price * quantity; // Cộng dồn tổng thanh toán
    });

    const totalPaymentElement = document.querySelector('.all-muahang div span:nth-child(2)');
    if (totalPaymentElement) {
        totalPaymentElement.innerHTML = `<u style="font-size: 10px;">đ</u>${totalAmount}đ`;
    } else {
        console.error('Phần tử tổng thanh toán không tồn tại');
    }
}

function removeFromCart() {
	localStorage.removeItem('cartItem');
	loadCartItem();
}

