-- =======================================================
-- OPENCART DATABASE TESTING
-- =======================================================

-- =======================================================
-- CUSTOMER TABLES TESTING
-- =======================================================

-- Basic Customer Data Test
SELECT 
    customer_id,
    CONCAT(firstname, ' ', lastname) as full_name,
    email,
    telephone,
    status,
    date_added
FROM oc_customer 
WHERE status = 1 
ORDER BY date_added DESC
LIMIT 10;

-- Customer Statistics
SELECT 
    COUNT(*) as total_customers,
    COUNT(CASE WHEN status = 1 THEN 1 END) as active_customers,
    COUNT(CASE WHEN status = 0 THEN 1 END) as inactive_customers
FROM oc_customer;

-- Customer Address Test
SELECT 
    a.address_id,
    CONCAT(c.firstname, ' ', c.lastname) as customer_name,
    a.address_1,
    a.city,
    a.postcode,
    co.name as country,
    z.name as zone
FROM oc_address a
LEFT JOIN oc_customer c ON a.customer_id = c.customer_id
LEFT JOIN oc_country co ON a.country_id = co.country_id
LEFT JOIN oc_zone z ON a.zone_id = z.zone_id
WHERE c.status = 1
LIMIT 10;

-- Customer Groups Test
SELECT 
    cg.customer_group_id,
    cg.approval,
    cg.sort_order,
    cgd.name as group_name,
    cgd.description
FROM oc_customer_group cg
LEFT JOIN oc_customer_group_description cgd 
    ON cg.customer_group_id = cgd.customer_group_id 
    AND cgd.language_id = 1
ORDER BY cg.sort_order;

-- =======================================================
-- PRODUCT TABLES TESTING
-- =======================================================

-- Basic Product Information Test
SELECT 
    p.product_id,
    p.model,
    p.sku,
    pd.name,
    p.price,
    p.quantity,
    p.status,
    p.viewed,
    p.date_added
FROM oc_product p
LEFT JOIN oc_product_description pd 
    ON p.product_id = pd.product_id 
    AND pd.language_id = 1
WHERE p.status = 1
ORDER BY p.date_added DESC
LIMIT 10;

-- Product Statistics
SELECT 
    COUNT(*) as total_products,
    COUNT(CASE WHEN status = 1 THEN 1 END) as active_products,
    COUNT(CASE WHEN status = 0 THEN 1 END) as inactive_products,
    AVG(price) as average_price,
    SUM(quantity) as total_inventory
FROM oc_product;

-- Most Viewed Products Test
SELECT 
    p.product_id,
    pd.name,
    p.viewed,
    p.price,
    p.quantity
FROM oc_product p
LEFT JOIN oc_product_description pd 
    ON p.product_id = pd.product_id 
    AND pd.language_id = 1
WHERE p.status = 1
ORDER BY p.viewed DESC
LIMIT 10;

-- Low Stock Products Test
SELECT 
    p.product_id,
    pd.name,
    p.quantity,
    p.price,
    p.model
FROM oc_product p
LEFT JOIN oc_product_description pd 
    ON p.product_id = pd.product_id 
    AND pd.language_id = 1
WHERE p.quantity < 10 
    AND p.status = 1
ORDER BY p.quantity ASC
LIMIT 10;

-- Product Images Test
SELECT 
    p.product_id,
    pd.name,
    p.image as main_image,
    COUNT(pi.image) as additional_images
FROM oc_product p
LEFT JOIN oc_product_description pd 
    ON p.product_id = pd.product_id 
    AND pd.language_id = 1
LEFT JOIN oc_product_image pi ON p.product_id = pi.product_id
WHERE p.status = 1
GROUP BY p.product_id
ORDER BY additional_images DESC
LIMIT 10;

-- =======================================================
-- CATEGORY TABLES TESTING
-- =======================================================

-- Main Categories Test
SELECT 
    c.category_id,
    cd.name,
    c.parent_id,
    c.sort_order,
    c.status
FROM oc_category c
LEFT JOIN oc_category_description cd 
    ON c.category_id = cd.category_id 
    AND cd.language_id = 1
WHERE c.parent_id = 0 
    AND c.status = 1
ORDER BY c.sort_order;

-- Category Hierarchy Test
SELECT 
    parent.category_id as parent_id,
    parent_desc.name as parent_name,
    child.category_id as child_id,
    child_desc.name as child_name,
    child.sort_order
FROM oc_category parent
LEFT JOIN oc_category_description parent_desc 
    ON parent.category_id = parent_desc.category_id 
    AND parent_desc.language_id = 1
LEFT JOIN oc_category child ON parent.category_id = child.parent_id
LEFT JOIN oc_category_description child_desc 
    ON child.category_id = child_desc.category_id 
    AND child_desc.language_id = 1
WHERE parent.parent_id = 0 
    AND parent.status = 1 
    AND child.status = 1
ORDER BY parent.sort_order, child.sort_order;

-- Products Per Category Test
SELECT 
    c.category_id,
    cd.name as category_name,
    COUNT(pc.product_id) as product_count
FROM oc_category c
LEFT JOIN oc_category_description cd 
    ON c.category_id = cd.category_id 
    AND cd.language_id = 1
LEFT JOIN oc_product_to_category pc ON c.category_id = pc.category_id
LEFT JOIN oc_product p ON pc.product_id = p.product_id AND p.status = 1
WHERE c.status = 1
GROUP BY c.category_id
ORDER BY product_count DESC
LIMIT 10;

-- =======================================================
-- ORDER TABLES TESTING
-- =======================================================

-- Recent Orders Test
SELECT 
    o.order_id,
    o.invoice_no,
    CONCAT(o.firstname, ' ', o.lastname) as customer_name,
    o.email,
    o.total,
    os.name as status,
    o.date_added
FROM oc_order o
LEFT JOIN oc_order_status os 
    ON o.order_status_id = os.order_status_id 
    AND os.language_id = 1
ORDER BY o.date_added DESC
LIMIT 10;

-- Order Statistics Test
SELECT 
    COUNT(*) as total_orders,
    SUM(total) as total_sales,
    AVG(total) as average_order_value,
    MIN(total) as min_order,
    MAX(total) as max_order
FROM oc_order
WHERE order_status_id > 0;

-- Orders by Status Test
SELECT 
    os.name as status,
    COUNT(o.order_id) as order_count,
    SUM(o.total) as total_sales
FROM oc_order o
LEFT JOIN oc_order_status os 
    ON o.order_status_id = os.order_status_id 
    AND os.language_id = 1
GROUP BY o.order_status_id
ORDER BY order_count DESC;

-- Order Products Test
SELECT 
    op.order_id,
    op.name as product_name,
    op.model,
    op.quantity,
    op.price,
    op.total
FROM oc_order_product op
WHERE op.order_id IN (
    SELECT order_id FROM oc_order 
    ORDER BY date_added DESC 
    LIMIT 5
)
ORDER BY op.order_id DESC;

-- =======================================================
-- SHOPPING CART AND COUPONS TESTING
-- =======================================================

-- Active Shopping Carts Test
SELECT 
    c.cart_id,
    cust.firstname,
    cust.lastname,
    pd.name as product_name,
    c.quantity,
    c.date_added
FROM oc_cart c
LEFT JOIN oc_customer cust ON c.customer_id = cust.customer_id
LEFT JOIN oc_product_description pd 
    ON c.product_id = pd.product_id 
    AND pd.language_id = 1
WHERE cust.status = 1
ORDER BY c.date_added DESC
LIMIT 10;

-- Active Coupons Test
SELECT 
    coupon_id,
    name,
    code,
    type,
    discount,
    date_start,
    date_end,
    uses_total,
    uses_customer,
    status
FROM oc_coupon
WHERE status = 1 
    AND (date_start = '0000-00-00' OR date_start <= NOW())
    AND (date_end = '0000-00-00' OR date_end >= NOW())
ORDER BY date_start DESC;

-- =======================================================
-- REVIEWS AND RATINGS TESTING
-- =======================================================

-- Recent Product Reviews Test
SELECT 
    r.review_id,
    pd.name as product_name,
    CONCAT(c.firstname, ' ', c.lastname) as reviewer_name,
    r.rating,
    LEFT(r.text, 100) as review_excerpt,
    r.status,
    r.date_added
FROM oc_review r
LEFT JOIN oc_product_description pd 
    ON r.product_id = pd.product_id 
    AND pd.language_id = 1
LEFT JOIN oc_customer c ON r.customer_id = c.customer_id
WHERE r.status = 1
ORDER BY r.date_added DESC
LIMIT 10;

-- Product Ratings Summary Test
SELECT 
    p.product_id,
    pd.name as product_name,
    COUNT(r.review_id) as total_reviews,
    ROUND(AVG(r.rating), 2) as average_rating
FROM oc_product p
LEFT JOIN oc_product_description pd 
    ON p.product_id = pd.product_id 
    AND pd.language_id = 1
LEFT JOIN oc_review r ON p.product_id = r.product_id 
    AND r.status = 1
WHERE p.status = 1
GROUP BY p.product_id
HAVING total_reviews > 0
ORDER BY average_rating DESC, total_reviews DESC
LIMIT 10;

-- =======================================================
-- SYSTEM SETTINGS TESTING
-- =======================================================

-- Store Configuration Test
SELECT 
    `key`,
    `value`
FROM oc_setting
WHERE store_id = 0 
    AND `code` = 'config'
    AND `key` IN ('config_name', 'config_owner', 'config_email', 'config_telephone')
ORDER BY `key`;

-- Active Languages Test
SELECT 
    language_id,
    name,
    code,
    locale,
    directory,
    sort_order,
    status
FROM oc_language
WHERE status = 1
ORDER BY sort_order;

-- Active Currencies Test
SELECT 
    currency_id,
    title,
    code,
    symbol_left,
    symbol_right,
    decimal_place,
    value,
    status
FROM oc_currency
WHERE status = 1
ORDER BY title;

-- Tax Rates Test
SELECT 
    tr.tax_rate_id,
    tr.name,
    tr.rate,
    tr.type,
    gz.name as geo_zone
FROM oc_tax_rate tr
LEFT JOIN oc_geo_zone gz ON tr.geo_zone_id = gz.geo_zone_id
ORDER BY tr.name;

-- =======================================================
-- CONTENT AND MARKETING TESTING
-- =======================================================

-- Information Pages Test
SELECT 
    i.information_id,
    id.title,
    i.bottom,
    i.sort_order,
    i.status
FROM oc_information i
LEFT JOIN oc_information_description id 
    ON i.information_id = id.information_id 
    AND id.language_id = 1
WHERE i.status = 1
ORDER BY i.sort_order;

-- SEO URLs Test
SELECT 
    seo_url_id,
    store_id,
    language_id,
    query,
    keyword
FROM oc_seo_url
WHERE language_id = 1
ORDER BY query
LIMIT 20;

-- =======================================================
-- LAYOUT AND DESIGN TESTING
-- =======================================================

-- Available Layouts Test
SELECT 
    layout_id,
    name
FROM oc_layout
ORDER BY name;

-- Layout Modules Test
SELECT 
    l.name as layout_name,
    lm.code as module_code,
    lm.position,
    lm.sort_order
FROM oc_layout_module lm
LEFT JOIN oc_layout l ON lm.layout_id = l.layout_id
ORDER BY l.name, lm.position, lm.sort_order;

-- Installed Extensions Test
SELECT 
    extension_id,
    type,
    code
FROM oc_extension
ORDER BY type, code;

-- =======================================================
-- USER AND PERMISSIONS TESTING
-- =======================================================

-- Admin Users Test
SELECT 
    u.user_id,
    u.username,
    CONCAT(u.firstname, ' ', u.lastname) as full_name,
    ug.name as user_group,
    u.status,
    u.date_added
FROM oc_user u
LEFT JOIN oc_user_group ug ON u.user_group_id = ug.user_group_id
WHERE u.status = 1
ORDER BY u.username;

-- User Groups Test
SELECT 
    user_group_id,
    name,
    permission
FROM oc_user_group
ORDER BY name;

-- =======================================================
-- SALES REPORTS TESTING
-- =======================================================

-- Daily Sales Report Last 7 Days
SELECT 
    DATE(date_added) as sale_date,
    COUNT(*) as total_orders,
    SUM(total) as daily_sales,
    AVG(total) as avg_order_value
FROM oc_order
WHERE order_status_id > 0 
    AND DATE(date_added) >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)
GROUP BY DATE(date_added)
ORDER BY sale_date DESC;

-- 2. Best Selling Products Report
SELECT 
    op.name as product_name,
    op.model,
    SUM(op.quantity) as total_sold,
    SUM(op.total) as total_revenue
FROM oc_order_product op
INNER JOIN oc_order o ON op.order_id = o.order_id
WHERE o.order_status_id IN (2, 3, 5)  -- Complete order statuses
GROUP BY op.product_id
ORDER BY total_sold DESC
LIMIT 15;

-- 3. Customer Purchase Summary
SELECT 
    c.customer_id,
    CONCAT(c.firstname, ' ', c.lastname) as customer_name,
    c.email,
    COUNT(o.order_id) as total_orders,
    SUM(o.total) as total_spent,
    MAX(o.date_added) as last_order_date
FROM oc_customer c
LEFT JOIN oc_order o ON c.customer_id = o.customer_id 
    AND o.order_status_id > 0
WHERE c.status = 1
GROUP BY c.customer_id
HAVING total_orders > 0
ORDER BY total_spent DESC
LIMIT 10;

-- =======================================================
-- 🔍 PRODUCT SEARCH & FILTERS TESTING
-- =======================================================

-- 1. Product Attributes Test
SELECT 
    p.product_id,
    pd.name as product_name,
    ad.name as attribute_name,
    pa.text as attribute_value
FROM oc_product_attribute pa
LEFT JOIN oc_product p ON pa.product_id = p.product_id
LEFT JOIN oc_product_description pd 
    ON p.product_id = pd.product_id 
    AND pd.language_id = 1
LEFT JOIN oc_attribute_description ad 
    ON pa.attribute_id = ad.attribute_id 
    AND ad.language_id = 1
WHERE p.status = 1 
    AND pa.language_id = 1
LIMIT 15;

-- =======================================================
-- ✅ DATABASE HEALTH CHECK
-- =======================================================

-- 1. Table Row Counts
SELECT 
    'oc_customer' as table_name, COUNT(*) as row_count FROM oc_customer
UNION ALL
SELECT 'oc_product', COUNT(*) FROM oc_product
UNION ALL
SELECT 'oc_order', COUNT(*) FROM oc_order
UNION ALL
SELECT 'oc_category', COUNT(*) FROM oc_category
UNION ALL
SELECT 'oc_review', COUNT(*) FROM oc_review
UNION ALL
SELECT 'oc_cart', COUNT(*) FROM oc_cart
ORDER BY row_count DESC;

-- 2. Active vs Inactive Records
SELECT 
    'Customers' as record_type,
    COUNT(CASE WHEN status = 1 THEN 1 END) as active_records,
    COUNT(CASE WHEN status = 0 THEN 1 END) as inactive_records
FROM oc_customer
UNION ALL
SELECT 
    'Products',
    COUNT(CASE WHEN status = 1 THEN 1 END),
    COUNT(CASE WHEN status = 0 THEN 1 END)
FROM oc_product
UNION ALL
SELECT 
    'Categories',
    COUNT(CASE WHEN status = 1 THEN 1 END),
    COUNT(CASE WHEN status = 0 THEN 1 END)
FROM oc_category;

-- =======================================================
-- 🎯 PERFORMANCE TESTING QUERIES
-- =======================================================

-- 1. Slow Query Test - Complex Product Search
SELECT 
    p.product_id,
    pd.name,
    p.price,
    p.quantity,
    cd.name as category,
    COUNT(r.review_id) as review_count,
    AVG(r.rating) as avg_rating
FROM oc_product p
LEFT JOIN oc_product_description pd 
    ON p.product_id = pd.product_id 
    AND pd.language_id = 1
LEFT JOIN oc_product_to_category pc ON p.product_id = pc.product_id
LEFT JOIN oc_category_description cd 
    ON pc.category_id = cd.category_id 
    AND cd.language_id = 1
LEFT JOIN oc_review r ON p.product_id = r.product_id 
    AND r.status = 1
WHERE p.status = 1
GROUP BY p.product_id
ORDER BY review_count DESC, avg_rating DESC
LIMIT 20;


-- Use this if the above country queries fail

SELECT 
    c.country_id,
    cd.name,
    c.iso_code_2,
    c.iso_code_3,
    c.address_format_id,
    c.postcode_required,
    c.status
FROM oc_country c
LEFT JOIN oc_country_description cd 
    ON c.country_id = cd.country_id 
    AND cd.language_id = 1
WHERE c.status = 1 
ORDER BY cd.name ASC
LIMIT 10;

-- Alternative Zone Test (using descriptions)
SELECT 
    z.zone_id,
    zd.name as zone_name,
    cd.name as country_name,
    z.code,
    z.status
FROM oc_zone z
LEFT JOIN oc_zone_description zd 
    ON z.zone_id = zd.zone_id 
    AND zd.language_id = 1
LEFT JOIN oc_country c ON z.country_id = c.country_id
LEFT JOIN oc_country_description cd 
    ON c.country_id = cd.country_id 
    AND cd.language_id = 1
WHERE z.status = 1 
ORDER BY cd.name, zd.name
LIMIT 15;

-- Simple Table Existence Check
-- Run this first to check if tables exist
SELECT 
    TABLE_NAME,
    TABLE_TYPE,
    ENGINE
FROM INFORMATION_SCHEMA.TABLES 
WHERE TABLE_SCHEMA = 'opencart' 
    AND TABLE_NAME LIKE 'oc_%'
ORDER BY TABLE_NAME;

-- Column Check for oc_country
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    IS_NULLABLE,
    COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_SCHEMA = 'opencart' 
    AND TABLE_NAME = 'oc_country'
ORDER BY ORDINAL_POSITION;

-- Column Check for oc_zone  
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    IS_NULLABLE,
    COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_SCHEMA = 'opencart' 
    AND TABLE_NAME = 'oc_zone'
ORDER BY ORDINAL_POSITION;

-- Check if data exists in main tables
SELECT 'oc_customer' as table_name, COUNT(*) as total_records FROM oc_customer
UNION ALL
SELECT 'oc_product', COUNT(*) FROM oc_product
UNION ALL  
SELECT 'oc_order', COUNT(*) FROM oc_order
UNION ALL
SELECT 'oc_category', COUNT(*) FROM oc_category
UNION ALL
SELECT 'oc_country', COUNT(*) FROM oc_country
UNION ALL
SELECT 'oc_zone', COUNT(*) FROM oc_zone;

-- =======================================================
-- BASIC CONNECTIVITY TESTS
-- =======================================================

-- Test 1: Simple SELECT
SELECT 1 as test_connection;

-- Test 2: Current Database
SELECT DATABASE() as current_database;

-- Test 3: MySQL Version
SELECT VERSION() as mysql_version;

-- Test 4: Current Time
SELECT NOW() as current_time;

-- Test 5: Count All Tables with oc_ prefix
SELECT COUNT(*) as total_opencart_tables
FROM INFORMATION_SCHEMA.TABLES 
WHERE TABLE_SCHEMA = DATABASE() 
    AND TABLE_NAME LIKE 'oc_%';
