-- Tạo cơ sở dữ liệu shoppe
create database shoppe;
go

-- Sử dụng cơ sở dữ liệu shoppe
use shoppe;
go

-- Tạo bảng Roles
create table Role(
    Id varchar(10) primary key not null,
    Names nvarchar(50) not null
);
go

-- Tạo bảng Accounts với cột Id là khóa chính
create table Account(
    Id int identity(1,1) primary key not null,
    Username varchar(20) not null unique,
    Password nvarchar(100) not null,
    Fullname nvarchar(50) not null,
    Email nvarchar(50) not null,
    Photo nvarchar(50)
);
go

-- Tạo bảng Authorities
create table Authorities(
    Id int identity(1,1) primary key not null,
    Username varchar(20) not null,
    RoleId varchar(10) not null,
    foreign key(Username) references Account(Username),
    foreign key(RoleId) references Role(Id)
);
go

-- Tạo bảng Categories
create table Categories(
    Id int identity(1,1) primary key not null,
    Name nvarchar(50) not null
);
go

-- Tạo bảng Products
create table Products(
    Id int identity(1,1) primary key not null,
    Name nvarchar(100) not null,
    Description nvarchar(255),
    Price decimal(10, 2) not null,
    Stock int not null,
    CategoryId int not null,
    foreign key(CategoryId) references Categories(Id)
);
go

-- Tạo bảng Orders
create table Orders(
    Id int identity(1,1) primary key not null,
    Username varchar(20) not null,
    OrderDate datetime not null,
    Status nvarchar(20) not null,
    foreign key(Username) references Account(Username)
);
go

-- Tạo bảng OrderDetails
create table OrderDetails(
    Id int identity(1,1) primary key not null,
    OrderId int not null,
    ProductId int not null,
    Quantity int not null,
    Price decimal(10, 2) not null,
    foreign key(OrderId) references Orders(Id),
    foreign key(ProductId) references Products(Id)
);
go

-- Tạo bảng UserDetails với thông tin chi tiết người dùng
create table UserDetails(
    UserId int primary key not null,
    StreetAddress nvarchar(100),
    City nvarchar(50),
    State nvarchar(50),
    ZipCode varchar(10),
    Country nvarchar(50),
    PhoneNumber varchar(15),
    DateOfBirth date,
    PlaceOfBirth nvarchar(100),
    foreign key(UserId) references Account(Id)
);
go

-- Tạo bảng Comments
create table Comments(
    Id int identity(1,1) primary key not null,
    ProductId int not null,
    Username varchar(20) not null,
    CommentText nvarchar(1000) not null,
    CommentDate datetime not null,
    foreign key(ProductId) references Products(Id),
    foreign key(Username) references Account(Username)
);
go

-- Tạo bảng Sizes để lưu trữ các kích thước sản phẩm
create table Sizes(
    Id int identity(1,1) primary key not null,
    SizeName nvarchar(50) not null
);
go

-- Tạo bảng Colors để lưu trữ các màu sắc sản phẩm
create table Colors(
    Id int identity(1,1) primary key not null,
    ColorName nvarchar(50) not null
);
go

-- Tạo bảng ProductAttributes để liên kết sản phẩm với kích thước và màu sắc
create table ProductAttributes(
    Id int identity(1,1) primary key not null,
    ProductId int not null,
    SizeId int not null,
    ColorId int not null,
    Stock int not null,
    Price decimal(10, 2),
    foreign key(ProductId) references Products(Id),
    foreign key(SizeId) references Sizes(Id),
    foreign key(ColorId) references Colors(Id)
);
go
create table ProductImages(
    Id int identity(1,1) primary key not null,
    ProductId int null, -- Tham chiếu đến bảng Products (nếu là hình ảnh chung cho sản phẩm)
    AttributeId int null, -- Tham chiếu đến bảng ProductAttributes (nếu là hình ảnh của biến thể)
    Image nvarchar(255) not null, -- Đường dẫn hoặc tên file hình ảnh
    ImageType nvarchar(50) not null, -- Loại hình ảnh (ví dụ: "main", "thumbnail", "detail")
    foreign key(ProductId) references Products(Id),
    foreign key(AttributeId) references ProductAttributes(Id)
);

alter table Products
add UserId int not null, -- Thêm cột UserId để lưu thông tin người đăng
foreign key(UserId) references Account(Id);

ALTER TABLE Products
ADD UserId INT NULL, -- Thêm cột UserId, cho phép NULL
FOREIGN KEY (UserId) REFERENCES Account(Id);

ALTER TABLE Products
DROP COLUMN UserId;

INSERT INTO Account (Username, Password, Fullname, Email, Photo)
VALUES ('truong', '123','N''Nguyễn Đình Trưởng', 'truongprotv', 'photo_value');
INSERT INTO Role (Id, Names) VALUES ('USER', 'User');
INSERT INTO Role (Id, Names) VALUES ('ADMIN', 'Admin');

INSERT INTO Authorities (Username, RoleId) VALUES ('truong', 'USER');

update Account set photo=N'luffy.jpg' where id =2

-- Cấp quyền 'ADMIN' cho người dùng 'jane_doe'
INSERT INTO Authorities (Username, RoleId) VALUES ('truong', 'ADMIN');



SELECT * FROM Authorities;
SELECT * FROM Account;
SELECT * FROM Role;
SELECT * FROM UserDetails;
select * from ProductImages
select * from Categories
SELECT * FROM Products;
SELECT * FROM ProductAttributes;
SELECT * FROM colors;
SELECT * FROM sizes;



alter table Products
add Image nvarchar(255);

alter table Products
drop COLUMN  Image;
INSERT INTO UserDetails (
    UserId,
    StreetAddress,
    City,
    State,
    ZipCode,
    Country,
    PhoneNumber,
    DateOfBirth,
    PlaceOfBirth
) VALUES (
    2, -- UserId
    'Nam Từ Niêm', -- StreetAddress
    N'Hà Nội', -- City
     N'Hà Nội', -- State
    '700000', -- ZipCode
    N'Việt Name', -- Country
    '0123456789', -- PhoneNumber
    '1990-01-01', -- DateOfBirth
    'Ho Hà Nội' -- PlaceOfBirth
);
select * from account 
inner join UserDetails on account.id = UserDetails.UserId where id=2
EXEC sp_columns @table_name = 'Products';
-- Thêm kích thước vào bảng Sizes
INSERT INTO Sizes (SizeName)
VALUES 
    ('S'),
    ('M'),
    ('L'),
    ('XL');

-- Thêm màu sắc vào bảng Colors
INSERT INTO Colors (ColorName)
VALUES 
    ('Đen'),
    ('Trắng'),
    ('Xanh'),
    ('Đỏ');
-- Thêm thuộc tính sản phẩm vào bảng ProductAttributes
INSERT INTO ProductAttributes (ProductId, SizeId, ColorId, Stock, Price)
VALUES 
    (3, 1, 2, 50, 250000),  -- Sản phẩm 1, kích thước S, màu Trắng
    (4, 2, 2, 30, 250000),  -- Sản phẩm 1, kích thước M, màu Trắng
    (5, 1, 1, 70, 350000),  -- Sản phẩm 2, kích thước S, màu Đen
    (6, 3, 1, 50, 350000),  -- Sản phẩm 2, kích thước L, màu Đen
    (7, 2, 3, 40, 600000),  -- Sản phẩm 3, kích thước M, màu Xanh
    (3, 4, 3, 20, 600000),  -- Sản phẩm 3, kích thước XL, màu Xanh
    (4, 1, 4, 25, 150000),  -- Sản phẩm 4, kích thước S, màu Đỏ
    (5, 2, 1, 10, 1200000); -- Sản phẩm 5, kích thước M, màu Đen
	-- Ví dụ chèn sản phẩm vào bảng Products
INSERT INTO Products (Name, Description, Price, Stock, CategoryId, UserId)
VALUES 
    ('Áo sơ mi trắng', 'Áo sơ mi trắng cao cấp', 250000, 100, 1, 1),
    ('Quần jeans xanh', 'Quần jeans màu xanh dương', 350000, 150, 1, 1),
    ('Giày thể thao', 'Giày thể thao thời trang', 600000, 200, 2, 1),
    ('Mũ lưỡi trai', 'Mũ lưỡi trai chất liệu vải', 150000, 80, 3, 2),
    ('Đồng hồ đeo tay', 'Đồng hồ thời trang cao cấp', 1200000, 50, 2, 2);
	-- Thêm danh mục vào bảng Categories
INSERT INTO Categories (Name)
VALUES 
    ('Áo quần'),      -- Ví dụ danh mục 1
    ('Giày dép'),     -- Ví dụ danh mục 2
    ('Phụ kiện');     -- Ví dụ danh mục 3


	INSERT INTO ProductImages (ProductId, AttributeId, Image, ImageType)
VALUES 
    (3, 4, 'ao_so_mi_trang.jpg', 'main'),
	(4, 5, 'ao_so_mi_trang.jpg', 'main')  ,
	(7, 6, 'ao_so_mi_trang.jpg', 'main')  ,
	(5, 5, 'ao_so_mi_trang.jpg', 'main')  ,
	(3, 5, 'ao_so_mi_trang.jpg', 'main')  
	-- Hình ảnh chính cho sản phẩm 1
	select * from Products inner join Categories on Categories.id = Products.CategoryId inner join
	ProductImages on ProductImages.ProductId = Products.Id where Products.id =3
	SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'ProductImage';
SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'ProductImage';


INSERT INTO ProductAttributes (ProductId, SizeId, ColorId, Stock, Price)
VALUES (3, (SELECT Id FROM Sizes WHERE SizeName = 'M'), (SELECT Id FROM Colors WHERE ColorName = 'Xanh'), 50, 100000.00);



INSERT INTO ProductImages (ProductId, AttributeId, Image, ImageType)
VALUES 
    (4, 4, 'anh6.jpg', 'detail'),
    (4, 4, 'anh7.jpg', 'thumbnail'),
    (4, 5, 'anh8.jpg', 'detail'),
    (4, 5, 'anh9.jpg', 'thumbnail'),
    (4, 6, 'anh9.jpg', 'detail'),
    (4, 6, 'anh10.jpg', 'thumbnail');