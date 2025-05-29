# 🛠️ Marketplace Servicii Locale

Platformă web completă pentru conectarea clienților cu prestatori locali (instalatori, zugravi, dădace etc.), cu autentificare prin Keycloak și plăți Stripe pentru abonamente.

---

## 🔧 Tehnologii folosite

- ✅ **Spring Boot (Java 17)**
- ✅ **PostgreSQL + Liquibase**
- ✅ **Keycloak** (auth & user management)
- ✅ **Stripe** (abonamente plătite pentru prestatori)
- ✅ **Scheduled Tasks** (pentru dezactivare automată)
- ✅ REST API organizat modular

---

## 🧩 Funcționalități principale

### 🔐 Autentificare & Înregistrare
- Integrare completă cu Keycloak
- Salvare utilizatori în baza de date
- Separare roluri: `CLIENT`, `PROVIDER`, `ADMIN`

### 👷 Prestatori de servicii
- Creează/editează profilul
- Adaugă servicii (titlu, preț, durată)
- Poate fi activat prin trial de 7 zile sau abonament plătit

### 📅 Programări
- Clienții pot face programări la servicii disponibile
- Fiecare programare are adresă, dată, status

### 💬 Recenzii
- Doar clienții cu programare completată pot lăsa recenzii

### 📦 Abonamente (Stripe)
- Abonamente lunare pentru prestatori (ex: 10€/lună)
- Se folosesc Stripe Checkout + Webhooks
- Trial automat de 7 zile la înregistrare

### 🛎️ Notificări
- Sistem simplu de notificări interne
- Exemple: „Ai primit o nouă programare”, „Abonamentul tău a expirat”

---

## 🏗️ Structură module
```bash
subscription/
├── controller/
│ ├── SubscriptionController
│ ├── PaymentController
│ └── StripeWebhookController
├── service/
│ ├── SubscriptionService
│ ├── StripeCheckoutService
│ └── SubscriptionCleanupJob (cron)
├── repository/
├── model/
```