--liquibase formatted sql

--changeset autor:enable-uuid-extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

--changeset autor:create-users-table
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    keycloak_id UUID NOT NULL UNIQUE,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    role VARCHAR(50) NOT NULL, -- CLIENT / PROVIDER / ADMIN
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--changeset autor:create-service-providers-table
CREATE TABLE service_providers (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    category VARCHAR(100) NOT NULL,
    description TEXT,
    location VARCHAR(255) NOT NULL,
    rating DECIMAL(3,2) DEFAULT 0.0,
    stripe_account_id VARCHAR(255),
    is_active BOOLEAN DEFAULT FALSE
);

--changeset autor:create-subscriptions-table
CREATE TABLE subscriptions (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    provider_id UUID NOT NULL REFERENCES service_providers(id) ON DELETE CASCADE,
    stripe_subscription_id VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL, -- active, canceled, trialing
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    trial_start_date TIMESTAMP,
    trial_end_date TIMESTAMP
);

--changeset autor:create-services-table
CREATE TABLE services (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    provider_id UUID NOT NULL REFERENCES service_providers(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    duration_minutes INT NOT NULL,
    description TEXT,
    payment_method VARCHAR(100) DEFAULT 'direct'
);

--changeset autor:create-appointments-table
CREATE TABLE appointments (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    service_id UUID NOT NULL REFERENCES services(id) ON DELETE CASCADE,
    client_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    appointment_time TIMESTAMP NOT NULL,
    address TEXT NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING', -- PENDING, CONFIRMED, COMPLETED, CANCELED
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--changeset autor:create-reviews-table
CREATE TABLE reviews (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    service_id UUID NOT NULL REFERENCES services(id) ON DELETE CASCADE,
    client_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_review_per_client_service UNIQUE (client_id, service_id)
);

--changeset autor:create-notifications-table
CREATE TABLE notifications (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    message TEXT NOT NULL,
    type VARCHAR(50) NOT NULL, -- APPOINTMENT, REVIEW, SYSTEM
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--changeset autor:create-indexes
CREATE INDEX idx_services_provider_id ON services(provider_id);
CREATE INDEX idx_appointments_service_id ON appointments(service_id);
CREATE INDEX idx_appointments_client_id ON appointments(client_id);
CREATE INDEX idx_notifications_user_id ON notifications(user_id);
CREATE INDEX idx_subscriptions_provider_id ON subscriptions(provider_id);
