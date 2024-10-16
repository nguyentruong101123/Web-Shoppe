// Thay đổi số lượng sản phẩm
document.querySelector('.btn-decrease').addEventListener('click', function () {
    const input = document.querySelector('.quantity-control input');
    let value = parseInt(input.value);
    if (value > 1) {
        value--;
        input.value = value;
    }
});

document.querySelector('.btn-increase').addEventListener('click', function () {
    const input = document.querySelector('.quantity-control input');
    let value = parseInt(input.value);
    if (value < 100) { // Giới hạn tối đa là 100
        value++;
        input.value = value;
    }
});

document.addEventListener('DOMContentLoaded', function() {
    // Xử lý sự kiện click cho ảnh thumbnail
    document.querySelectorAll('.thumbnail-images .thumbnail').forEach(function(thumbnail) {
        thumbnail.addEventListener('click', function() {
            var newImageSrc = thumbnail.getAttribute('src'); // Lấy từ thuộc tính 'src'
            
            // Cập nhật ảnh chính
            document.querySelector('.main-image img').setAttribute('src', newImageSrc);
        });
    });

    // Xử lý sự kiện click cho nút cuộn trái
    document.querySelector('.scroll-button.left').addEventListener('click', function() {
        document.querySelector('.thumbnail-container').scrollBy({ left: -100, behavior: 'smooth' });
    });

    // Xử lý sự kiện click cho nút cuộn phải
    document.querySelector('.scroll-button.right').addEventListener('click', function() {
        document.querySelector('.thumbnail-container').scrollBy({ left: 100, behavior: 'smooth' });
    });
});

// Format giá thành tiền Việt Nam Đồng (VND)
function formatCurrency(price) {
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
}

// Xử lý khi hover màu sắc
function hoverColor(colorId) {
    const selectedColor = document.querySelector(`span.color-option[data-id="${colorId}"]`);
    if (selectedColor) {
        const image = selectedColor.getAttribute('data-image');
        const mainImageElement = document.querySelector('.main-image img');
        
        if (mainImageElement) {
            mainImageElement.src = `/assets/images/product/${image}`;
        }
    } else {
        console.error(`Color with ID ${colorId} not found`);
    }
}

// Hàm xử lý khi người dùng chọn kích thước
function selectSize(sizeElement) {
    const allSizeOptions = document.querySelectorAll('.size-option');
    allSizeOptions.forEach(option => {
        option.classList.remove('selected'); // Xóa class 'selected' khỏi tất cả kích thước
    });

    sizeElement.classList.add('selected'); // Thêm class 'selected' vào kích thước đã chọn
}

// Hàm xử lý khi người dùng chọn màu sắc
function selectColor(colorId) {
    const allColorOptions = document.querySelectorAll('.color-option');
    allColorOptions.forEach(option => {
        option.classList.remove('selected'); // Xóa class 'selected' khỏi tất cả màu
    });

    const selectedColor = document.querySelector(`span.color-option[data-id="${colorId}"]`);
    
    if (selectedColor) {
        selectedColor.classList.add('selected'); // Thêm class 'selected' vào màu đã chọn

        const price = selectedColor.getAttribute('data-price');
        const stock = selectedColor.getAttribute('data-stock');
        const image = selectedColor.getAttribute('data-image');
        const sizes = selectedColor.getAttribute('data-size').split(','); // Chia kích thước thành mảng

        const priceElement = document.querySelector('.product-price p');
        const stockElement = document.querySelector('.product-quantity span');
        const mainImageElement = document.querySelector('.main-image img');
        const sizeElements = document.querySelectorAll('.product-size .size-option');

        if (priceElement) {
            priceElement.innerText = `Giá: ${formatCurrency(price)}`;
        }
        
        if (stockElement) {
            stockElement.innerText = `Số lượng: ${stock}`;
        }
        
        if (mainImageElement) {
            mainImageElement.src = `/assets/images/product/${image}`;
        }

        // Làm mờ các kích thước nếu không có
        sizeElements.forEach(sizeElement => {
            if (!sizes.includes(sizeElement.innerText.trim())) {
                sizeElement.classList.add('disabled'); // Thêm class để làm mờ
            } else {
                sizeElement.classList.remove('disabled'); // Bỏ class nếu có
            }
        });
    } else {
        console.error(`Color with ID ${colorId} not found`);
    }
}

// Cập nhật ảnh chính
function updateMainImage(imageSrc) {
    const mainImageElement = document.querySelector('.main-image img');
    if (mainImageElement) {
        mainImageElement.src = `/assets/images/product/${imageSrc}`;
    } else {
        console.error('Main image element not found');
    }
}

// Xử lý khi ấn "Mua ngay"
function buyNow() {
    // Lấy thông tin màu sắc đã chọn
    const selectedColor = document.querySelector('.color-option.selected');
    const colorId = selectedColor ? selectedColor.getAttribute('data-id') : null;
    const colorName = selectedColor ? selectedColor.innerText : null; // Lấy tên màu sắc
    const price = selectedColor ? selectedColor.getAttribute('data-price') : null;
    const stock = selectedColor ? selectedColor.getAttribute('data-stock') : null;
    const image = selectedColor ? selectedColor.getAttribute('data-image') : null;

    // Lấy thông tin kích thước đã chọn
    const selectedSize = document.querySelector('.size-option.selected');
    const sizeName = selectedSize ? selectedSize.innerText : null; // Lấy tên kích thước

    // Lấy số lượng đã chọn
    const quantityInput = document.querySelector('.quantity-control input');
    const quantity = quantityInput ? quantityInput.value : null;

    // Lấy mã sản phẩm và tên sản phẩm từ HTML
    const productNameElement = document.querySelector('.product-name');
    const productId = productNameElement ? productNameElement.getAttribute('data-id') : null;
    const productName = productNameElement ? productNameElement.innerText : null;
    const mainImageElement = document.querySelector('.main-image img');
    const mainImage = mainImageElement ? mainImageElement.src : null; // Lấy đường dẫn ảnh chính
    const productDescriptionElement = document.querySelector('.product-description');
    const productDescription = productDescriptionElement ? productDescriptionElement.innerText : null;

    // Lấy tên shop
    const shopNameElement = document.querySelector('.shop-name'); // Thay đổi selector nếu cần
    const shopName = shopNameElement ? shopNameElement.innerText : null;

    // Lấy tất cả ảnh thumbnail
    const thumbnails = document.querySelectorAll('.thumbnail-images .thumbnail');
    const thumbnailImages = Array.from(thumbnails).map(thumbnail => {
        return thumbnail.getAttribute('src'); // Lấy đường dẫn của từng ảnh thumbnail
    });

    // Xóa thông báo cũ
    const messageElement = document.getElementById('productMessage');
    messageElement.innerText = '';

    // Kiểm tra xem người dùng đã chọn đầy đủ màu sắc, kích thước và số lượng hay chưa
    if (!colorId) {
        messageElement.innerText = "Vui lòng chọn màu sắc trước khi mua.";
        return;
    }

    if (!sizeName) {
        messageElement.innerText = "Vui lòng chọn kích thước trước khi mua.";
        return;
    }

    if (!quantity || quantity < 1) {
        messageElement.innerText = "Vui lòng chọn số lượng trước khi mua.";
        return;
    }

    // Tạo đối tượng sản phẩm để thêm vào giỏ hàng
    const product = {
        productId: productId,
        productName: productName,
        productDescription: productDescription,
        shopName: shopName, // Tên shop
        colorId: colorId,
        colorName: colorName, // Tên màu sắc
        sizeName: sizeName,
        price: price,
        quantity: quantity,
        mainImage: mainImage, // Ảnh chính
        thumbnailImages: thumbnailImages // Ảnh thumbnail
    };

    // Gửi thông tin sản phẩm đến giỏ hàng (ví dụ lưu trong localStorage hoặc điều hướng tới trang giỏ hàng)
   	let cart = JSON.parse(localStorage.getItem('cart')) || [];
    cart.push(product);  // Thêm sản phẩm mới vào giỏ hàng
    localStorage.setItem('cart', JSON.stringify(cart));  // Cập nhật lại giỏ hàng

    // Chuyển hướng người dùng tới trang giỏ hàng
    window.location.href = '/cart/sale';
}

