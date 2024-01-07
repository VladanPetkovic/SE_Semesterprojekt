@echo off

REM --------------------------------------------------
REM Monster Trading Cards Game
REM --------------------------------------------------
title Monster Trading Cards Game
echo CURL Testing for Monster Trading Cards Game -- updated by Vladan Petkovic
echo.

REM --------------------------------------------------
echo 1) Create Users (Registration)
REM Create User
curl -X POST http://localhost:10001/users --header "Content-Type: application/json" -d "{\"Username\":\"vladan\", \"Password\":\"password\"}"
echo.
curl -X POST http://localhost:10001/users --header "Content-Type: application/json" -d "{\"Username\":\"tom\", \"Password\":\"password\"}"
echo.
curl -X POST http://localhost:10001/users --header "Content-Type: application/json" -d "{\"Username\":\"alf\", \"Password\":\"password\"}"
echo.
curl -X POST http://localhost:10001/users --header "Content-Type: application/json" -d "{\"Username\":\"john\", \"Password\":\"password\"}"
echo.
curl -X POST http://localhost:10001/users --header "Content-Type: application/json" -d "{\"Username\":\"admin\",    \"Password\":\"istrator\"}"
echo.

echo should fail:
curl -X POST http://localhost:10001/users --header "Content-Type: application/json" -d "{\"Username\":\"vladan\", \"Password\":\"daniel\"}"
echo.
curl -X POST http://localhost:10001/users --header "Content-Type: application/json" -d "{\"Username\":\"vladan\", \"Password\":\"different\"}"
echo.
echo.

REM --------------------------------------------------
echo 2) Login Users
curl -X POST http://localhost:10001/sessions --header "Content-Type: application/json" -d "{\"Username\":\"vladan\", \"Password\":\"password\"}"
echo.
curl -X POST http://localhost:10001/sessions --header "Content-Type: application/json" -d "{\"Username\":\"tom\", \"Password\":\"password\"}"
echo.
curl -X POST http://localhost:10001/sessions --header "Content-Type: application/json" -d "{\"Username\":\"alf\", \"Password\":\"password\"}"
echo.
curl -X POST http://localhost:10001/sessions --header "Content-Type: application/json" -d "{\"Username\":\"john\", \"Password\":\"password\"}"
echo.
curl -X POST http://localhost:10001/sessions --header "Content-Type: application/json" -d "{\"Username\":\"admin\",    \"Password\":\"istrator\"}"
echo.

echo should fail:
curl -X POST http://localhost:10001/sessions --header "Content-Type: application/json" -d "{\"Username\":\"vladan\", \"Password\":\"different\"}"
echo.
echo.

REM --------------------------------------------------
echo 3) create packages (done by "admin")
curl -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"Id\":\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"Name\":\"Knight\", \"Damage\": 24.0}, {\"Id\":\"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\", \"Name\":\"WaterGoblin\", \"Damage\": 50.0}, {\"Id\":\"e85e3976-7c86-4d06-9a80-641c2019a79f\", \"Name\":\"FireGoblin\", \"Damage\": 30.0}, {\"Id\":\"1cb6ab86-bdb2-47e5-b6e4-68c5ab389334\", \"Name\":\"RegularGoblin\", \"Damage\": 45.0}, {\"Id\":\"dfdd758f-649c-40f9-ba3a-8657f4b3439f\", \"Name\":\"FireSpell\",    \"Damage\": 65.0}]"
echo.
curl -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"Id\":\"644808c2-f87a-4600-b313-122b02322fd5\", \"Name\":\"Dragon\", \"Damage\":  29.0}, {\"Id\":\"4a2757d6-b1c3-47ac-b9a3-91deab093531\", \"Name\":\"WaterElf\", \"Damage\": 45.0}, {\"Id\":\"91a6471b-1426-43f6-ad65-6fc473e16f9f\", \"Name\":\"FireTroll\", \"Damage\": 21.0}, {\"Id\":\"4ec8b269-0dfa-4f97-809a-2c63fe2a0025\", \"Name\":\"RegularTroll\", \"Damage\": 55.0}, {\"Id\":\"f8043c23-1534-4487-b66b-238e0c3c39b5\", \"Name\":\"RegularSpell\",   \"Damage\": 63.0}]"
echo.
curl -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"Id\":\"b017ee50-1c14-44e2-bfd6-2c0c5653a37c\", \"Name\":\"Ork\", \"Damage\": 32.0}, {\"Id\":\"d04b736a-e874-4137-b191-638e0ff3b4e7\", \"Name\":\"WaterTroll\", \"Damage\": 50.0}, {\"Id\":\"88221cfe-1f84-41b9-8152-8e36c6a354de\", \"Name\":\"FireElf\", \"Damage\": 32.0}, {\"Id\":\"1d3f175b-c067-4359-989d-96562bfa382c\", \"Name\":\"RegularElf\", \"Damage\": 40.0}, {\"Id\":\"171f6076-4eb5-4a7d-b3f2-2d650cc3d237\", \"Name\":\"WaterSpell\", \"Damage\": 68.0}]"
echo.
curl -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"Id\":\"ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8\", \"Name\":\"Kraken\", \"Damage\": 20.0}, {\"Id\":\"65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5\", \"Name\":\"WaterSpell\", \"Damage\": 50.0}, {\"Id\":\"55ef46c4-016c-4168-bc43-6b9b1e86414f\", \"Name\":\"FireTroll\", \"Damage\": 20.0}, {\"Id\":\"f3fad0f2-a1af-45df-b80d-2e48825773d9\", \"Name\":\"RegularSpell\", \"Damage\": 45.0}, {\"Id\":\"8c20639d-6400-4534-bd0f-ae563f11f57a\", \"Name\":\"RegularGoblin\",   \"Damage\": 65.0}]"
echo.
curl -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"Id\":\"d7d0cb94-2cbf-4f97-8ccf-9933dc5354b8\", \"Name\":\"Wizzard\", \"Damage\":  26.0}, {\"Id\":\"44c82fbc-ef6d-44ab-8c7a-9fb19a0e7c6e\", \"Name\":\"WaterGoblin\", \"Damage\": 45.0}, {\"Id\":\"2c98cd06-518b-464c-b911-8d787216cddd\", \"Name\":\"FireSpell\", \"Damage\": 21.0}, {\"Id\":\"951e886a-0fbf-425d-8df5-af2ee4830d85\", \"Name\":\"RegularSpell\", \"Damage\": 55.0}, {\"Id\":\"dcd93250-25a7-4dca-85da-cad2789f7198\", \"Name\":\"FireElf\",    \"Damage\": 63.0}]"
echo.
curl -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Bearer admin-mtcgToken" -d "[{\"Id\":\"b2237eca-0271-43bd-87f6-b22f70d42ca4\", \"Name\":\"Kraken\", \"Damage\": 71.0}, {\"Id\":\"9e8238a4-8a7a-487f-9f7d-a8c97899eb48\", \"Name\":\"WaterSpell\", \"Damage\": 50.0}, {\"Id\":\"d60e23cf-2238-4d49-844f-c7589ee5342e\", \"Name\":\"FireSpell\", \"Damage\": 18.0}, {\"Id\":\"fc305a7a-36f7-4d30-ad27-462ca0445649\", \"Name\":\"RegularSpell\", \"Damage\": 40.0}, {\"Id\":\"84d276ee-21ec-4171-a509-c1b88162831c\", \"Name\":\"Kraken\", \"Damage\": 68.0}]"
echo.
echo.

REM --------------------------------------------------
echo 4) acquire packages vladan
curl -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer vladan-mtcgToken" -d ""
echo.
curl -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer vladan-mtcgToken" -d ""
echo.
echo.

REM --------------------------------------------------
echo 4.1) acquire packages alf
curl -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer alf-mtcgToken" -d ""
echo.
echo.

REM --------------------------------------------------
echo 4.2) acquire packages john
curl -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer john-mtcgToken" -d ""
echo.
echo.

REM --------------------------------------------------
echo 5) acquire packages tom
curl -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer tom-mtcgToken" -d ""
echo.
curl -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer tom-mtcgToken" -d ""
echo.
echo should fail (no package):
curl -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Bearer tom-mtcgToken" -d ""
echo.
echo.

REM --------------------------------------------------
echo 6) show all acquired cards vladan
curl -X GET http://localhost:10001/cards --header "Authorization: Bearer vladan-mtcgToken"
echo should fail (no token)
curl -X GET http://localhost:10001/cards
echo.
echo.

REM --------------------------------------------------
echo 6.1) show all acquired cards alf
curl -X GET http://localhost:10001/cards --header "Authorization: Bearer alf-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 6.2) show all acquired cards john
curl -X GET http://localhost:10001/cards --header "Authorization: Bearer john-mtcgToken"
echo should fail (no token)
curl -X GET http://localhost:10001/cards
echo.
echo.

REM --------------------------------------------------
echo 7) show all acquired cards tom
curl -X GET http://localhost:10001/cards --header "Authorization: Bearer tom-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 8) show unconfigured deck
curl -X GET http://localhost:10001/deck --header "Authorization: Bearer vladan-mtcgToken"
echo.
curl -X GET http://localhost:10001/deck --header "Authorization: Bearer tom-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 9) configure deck
curl -X PUT http://localhost:10001/deck --header "Content-Type: application/json" --header "Authorization: Bearer vladan-mtcgToken" -d "[\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\", \"e85e3976-7c86-4d06-9a80-641c2019a79f\", \"1cb6ab86-bdb2-47e5-b6e4-68c5ab389334\"]"
echo.
curl -X PUT http://localhost:10001/deck --header "Content-Type: application/json" --header "Authorization: Bearer alf-mtcgToken" -d "[\"b017ee50-1c14-44e2-bfd6-2c0c5653a37c\", \"d04b736a-e874-4137-b191-638e0ff3b4e7\", \"88221cfe-1f84-41b9-8152-8e36c6a354de\", \"171f6076-4eb5-4a7d-b3f2-2d650cc3d237\"]"
echo.
curl -X PUT http://localhost:10001/deck --header "Content-Type: application/json" --header "Authorization: Bearer john-mtcgToken" -d "[\"ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8\", \"65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5\", \"f3fad0f2-a1af-45df-b80d-2e48825773d9\", \"8c20639d-6400-4534-bd0f-ae563f11f57a\"]"
echo.
curl -X PUT http://localhost:10001/deck --header "Content-Type: application/json" --header "Authorization: Bearer tom-mtcgToken" -d "[\"d7d0cb94-2cbf-4f97-8ccf-9933dc5354b8\", \"44c82fbc-ef6d-44ab-8c7a-9fb19a0e7c6e\", \"b2237eca-0271-43bd-87f6-b22f70d42ca4\", \"d60e23cf-2238-4d49-844f-c7589ee5342e\"]"
echo.
echo.

REM --------------------------------------------------
echo 10) show configured deck
curl -X GET http://localhost:10001/deck --header "Authorization: Bearer vladan-mtcgToken"
echo.
curl -X GET http://localhost:10001/deck --header "Authorization: Bearer alf-mtcgToken"
echo.
curl -X GET http://localhost:10001/deck --header "Authorization: Bearer john-mtcgToken"
echo.
curl -X GET http://localhost:10001/deck --header "Authorization: Bearer tom-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 11) show configured deck different representation
echo vladan
curl -X GET http://localhost:10001/deck?format=plain --header "Authorization: Bearer vladan-mtcgToken"
echo.
echo.
echo tom
curl -X GET http://localhost:10001/deck?format=plain --header "Authorization: Bearer tom-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 12) edit user data
echo.
curl -X GET http://localhost:10001/users/vladan --header "Authorization: Bearer vladan-mtcgToken"
echo.
curl -X GET http://localhost:10001/users/tom --header "Authorization: Bearer tom-mtcgToken"
echo.
curl -X PUT http://localhost:10001/users/vladan --header "Content-Type: application/json" --header "Authorization: Bearer vladan-mtcgToken" -d "{\"Name\": \"Vladan\",  \"Bio\": \"me playin...\", \"Image\": \":-)\"}"
echo.
curl -X PUT http://localhost:10001/users/tom --header "Content-Type: application/json" --header "Authorization: Bearer tom-mtcgToken" -d "{\"Name\": \"THOMAS\", \"Bio\": \"me codin...\",  \"Image\": \":-D\"}"
echo.
curl -X GET http://localhost:10001/users/vladan --header "Authorization: Bearer vladan-mtcgToken"
echo.
curl -X GET http://localhost:10001/users/tom --header "Authorization: Bearer tom-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 13) stats
curl -X GET http://localhost:10001/stats --header "Authorization: Bearer vladan-mtcgToken"
echo.
curl -X GET http://localhost:10001/stats --header "Authorization: Bearer alf-mtcgToken"
echo.
curl -X GET http://localhost:10001/stats --header "Authorization: Bearer john-mtcgToken"
echo.
curl -X GET http://localhost:10001/stats --header "Authorization: Bearer tom-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 14) scoreboard
curl -X GET http://localhost:10001/scoreboard --header "Authorization: Bearer vladan-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 15) battle
start /b "vladan battle" curl -X POST http://localhost:10001/battles --header "Authorization: Bearer vladan-mtcgToken"
start /b "tom battle" curl -X POST http://localhost:10001/battles --header "Authorization: Bearer tom-mtcgToken"
ping localhost -n 10 >NUL 2>NUL
echo.
echo.

REM --------------------------------------------------
echo 15.1) battle
start /b "alf battle" curl -X POST http://localhost:10001/battles --header "Authorization: Bearer alf-mtcgToken"
start /b "john battle" curl -X POST http://localhost:10001/battles --header "Authorization: Bearer john-mtcgToken"
ping localhost -n 10 >NUL 2>NUL
echo.
echo.

REM --------------------------------------------------
echo 16) Stats
echo vladan
curl -X GET http://localhost:10001/stats --header "Authorization: Bearer vladan-mtcgToken"
echo.
echo alf
curl -X GET http://localhost:10001/stats --header "Authorization: Bearer alf-mtcgToken"
echo.
echo john
curl -X GET http://localhost:10001/stats --header "Authorization: Bearer john-mtcgToken"
echo.
echo tom
curl -X GET http://localhost:10001/stats --header "Authorization: Bearer tom-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 17) scoreboard
curl -X GET http://localhost:10001/scoreboard --header "Authorization: Bearer alf-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo end...

REM this is approx a sleep
ping localhost -n 100 >NUL 2>NUL
@echo on
