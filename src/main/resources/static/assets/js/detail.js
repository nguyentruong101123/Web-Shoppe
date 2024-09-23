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
            // Lấy đường dẫn ảnh từ thuộc tính data-image
            var newImageSrc = thumbnail.getAttribute('data-image');
            
            // Cập nhật ảnh chính
            document.getElementById('main-image').setAttribute('src', '/assets/images/product/' + newImageSrc);
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
function formatCurrency(price) {
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
}


function hoverColor(colorId) {
    // Get the color ID, image, price, and stock from the element
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


function selectSize(sizeElement) {
    const allSizeOptions = document.querySelectorAll('.size-option');
    allSizeOptions.forEach(option => {
        option.classList.remove('selected'); // Xóa class 'selected' khỏi tất cả kích thước
    });

    sizeElement.classList.add('selected'); // Thêm class 'selected' vào kích thước đã chọn
}

// Thêm hàm này nếu cần để xử lý hover hiệu ứng border cho màu

function selectColor(colorId) {
    // Xóa class 'selected' khỏi tất cả màu sắc
    const allColorOptions = document.querySelectorAll('.color-option');
    allColorOptions.forEach(option => {
        option.classList.remove('selected');
    });

    const selectedColor = document.querySelector(`span.color-option[data-id="${colorId}"]`);
    
    if (selectedColor) {
        // Thêm class 'selected' vào màu đã chọn
        selectedColor.classList.add('selected');

        const price = selectedColor.getAttribute('data-price');
        const stock = selectedColor.getAttribute('data-stock');
        const image = selectedColor.getAttribute('data-image');
        const sizes = selectedColor.getAttribute('data-size').split(','); // Chia kích thước thành mảng

        const priceElement = document.querySelector('.product-price p');
        const stockElement = document.querySelector('.product-quantity span');
        const mainImageElement = document.querySelector('.main-image img');
        const sizeElements = document.querySelectorAll('.product-size .size-option');

        if (priceElement) {
            priceElement.innerText = `Giá: ${formatCurrency(price)} VND`;
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
  const allColorOptions = document.querySelectorAll('.color-option');
        allColorOptions.forEach(option => {
            option.classList.remove('selected'); // Xóa class 'selected' khỏi tất cả màu
        });
        
        selectedColor.classList.add('selected'); // Thêm class 'selected' cho màu đã chọn
    } else {
        console.error(`Color with ID ${colorId} not found`);
    }
}


function updateMainImage(imageSrc) {
    const mainImageElement = document.getElementById('mainImage');
    if (mainImageElement) {
        mainImageElement.src = `/assets/images/product/${imageSrc}`;
    } else {
        console.error('Main image element not found');
    }
}

function buyNow() {
    // Lấy thông tin sản phẩm
    const product = {
        id: details.product.id,
        name: details.product.name,
        price: document.querySelector('.product-price p').innerText.split(': ')[1], // Giá sản phẩm
        quantity: document.querySelector('.quantity-control input').value, // Số lượng
        size: selectedSize, // Kích thước đã chọn (cần được cập nhật khi chọn)
        color: selectedColor // Màu sắc đã chọn (cần được cập nhật khi chọn)
    };

    // Thêm sản phẩm vào giỏ hàng (có thể sử dụng dịch vụ giỏ hàng đã tạo trước đó)
    CartService.addToCart(product);

    // Chuyển hướng đến trang giỏ hàng
    window.location.href = '/cart'; // Thay đổi đường dẫn nếu cần
}
