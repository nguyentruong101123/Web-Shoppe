
function addProductAttribute() {
    const productAttributesContainer = document.getElementById('productAttributesContainer');


    // Tạo div mới cho thuộc tính sản phẩm
    const productAttributeDiv = document.createElement('div');
    productAttributeDiv.classList.add('product-attribute');


    // Tạo từng hàng (form-row) cho từng thuộc tính

    // Thêm hàng chọn màu sắc
    const colorRow = document.createElement('div');
    colorRow.classList.add('form-row');
    colorRow.innerHTML = `
        <label for="colorSelect">Màu:</label>
        <div class="select-container">
            <select name="colorIds[]" id="colorSelect" required>
                <option value="" disabled selected>Chọn màu sắc</option>
                ${colors.map(color => `<option value="${color.id}">${color.colorName}</option>`).join('')}
            </select>
            <button type="button" onclick="openCreateColorModal()">+ Tạo màu mới</button>
        </div>
    `;
    productAttributeDiv.appendChild(colorRow);

    // Thêm hàng chọn kích thước
    const sizeRow = document.createElement('div');
    sizeRow.classList.add('form-row');
    sizeRow.innerHTML = `
        <label for="sizeSelect">Kích thước:</label>
        <div class="select-container">
            <select name="sizeIds[]" id="sizeSelect" required>
                <option value="" disabled selected>Chọn kích thước</option>
                ${sizes.map(size => `<option value="${size.id}">${size.sizeName}</option>`).join('')}
            </select>
            <button type="button" onclick="openCreateSizeModal()">+ Tạo kích thước mới</button>
        </div>
    `;
    productAttributeDiv.appendChild(sizeRow);

    // Thêm hàng nhập giá
    const priceRow = document.createElement('div');
    priceRow.classList.add('form-row');
    priceRow.innerHTML = `
        <label for="priceInput">Giá:</label>
        <input type="number" name="attributePrices[]" id="priceInput" step="0.01" required>
    `;
    productAttributeDiv.appendChild(priceRow);

    // Thêm hàng nhập số lượng
    const stockRow = document.createElement('div');
    stockRow.classList.add('form-row');
    stockRow.innerHTML = `
        <label for="stockInput">Số lượng:</label>
        <input type="number" name="attributeStocks[]" id="stockInput" required>
    `;
    productAttributeDiv.appendChild(stockRow);

    // Thêm hàng nhập ảnh chi tiết
    const imageRow = document.createElement('div');
    imageRow.classList.add('form-row');
    imageRow.innerHTML = `
        <label for="detailImages">Ảnh chi tiết:</label>
        <input type="file" name="detailImages" id="detailImages" multiple>
    `;
    productAttributeDiv.appendChild(imageRow);

    // Thêm thuộc tính sản phẩm mới vào container
    productAttributesContainer.appendChild(productAttributeDiv);
}


	