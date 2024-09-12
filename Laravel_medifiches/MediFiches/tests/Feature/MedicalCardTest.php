<?php


namespace Tests\Feature;

use App\Models\MedicalCard;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Tests\TestCase;

class MedicalCardTest extends TestCase
{
    use RefreshDatabase;

    protected function setUp(): void
    {
        parent::setUp();

        MedicalCard::create([
            'national_number' => '111111111',
            'first_name' => 'Jane',
            'last_name' => 'Doe',
            'can_participate' => true,
            'doctor' => 'Dr. Johnson',
            'tetanos_protected' => true,
            'allergies' => 'None',
            'medecins' => 'Paracetamol',
            'birth_date' => '1985-05-15',
            'additional_infos' => 'No special notes for Jane',
            'street' => 'Oak Street',
            'no' => '5',
            'mail_box' => '789',
            'postal_code' => '54321',
            'city' => 'Townsville',
        ]);
    }

    public function testCreateMedicalCard()
    {
        // Arrange
        $data = [
            'national_number' => '123456789',
            'first_name' => 'John',
            'last_name' => 'Doe',
            'can_participate' => true,
            'doctor' => 'Dr. Smith',
            'tetanos_protected' => false,
            'allergies' => 'Pollen',
            'medecins' => 'Aspirin',
            'birth_date' => '1990-01-01',
            'additional_infos' => 'No special notes',
            'street' => 'Main Street',
            'no' => '42',
            'mail_box' => '567',
            'postal_code' => '12345',
            'city' => 'Cityville',
        ];

        // Act
        $medicalCard = MedicalCard::createMedicalCard($data);

        // Assert
        $this->assertInstanceOf(MedicalCard::class, $medicalCard);
        $this->assertDatabaseHas('medical_card', ['national_number' => '123456789']);
        $this->assertEquals('John', $medicalCard->first_name);
        $this->assertTrue($medicalCard->can_participate);
        $this->assertEquals('Dr. Smith', $medicalCard->doctor);
        $this->assertFalse($medicalCard->tetanos_protected);
        $this->assertEquals('Pollen', $medicalCard->allergies);
        $this->assertEquals('Aspirin', $medicalCard->medecins);
        $this->assertEquals('1990-01-01', $medicalCard->birth_date);
        $this->assertEquals('No special notes', $medicalCard->additional_infos);
        $this->assertEquals('Main Street', $medicalCard->street);
        $this->assertEquals('42', $medicalCard->no);
        $this->assertEquals('567', $medicalCard->mail_box);
        $this->assertEquals('12345', $medicalCard->postal_code);
        $this->assertEquals('Cityville', $medicalCard->city);
    }

    public function testUpdateMedicalCard()
    {
        // Arrange
        $originalData = [
            'national_number' => '123456789',
            'first_name' => 'John',
            'last_name' => 'Doe',
            'can_participate' => true,
            'doctor' => 'Dr. Smith',
            'tetanos_protected' => false,
            'allergies' => 'Pollen',
            'medecins' => 'Aspirin',
            'birth_date' => '1990-01-01',
            'additional_infos' => 'No special notes',
            'street' => 'Main Street',
            'no' => '42',
            'mail_box' => '567',
            'postal_code' => '12345',
            'city' => 'Cityville',
        ];

        $updatedData = [
            'first_name' => 'Updated John',
            'tetanos_protected' => true,
            'medecins' => 'Paracetamol',
            'postal_code' => '54321',
        ];

        // Crée une carte médicale avec les données d'origine
        $originalMedicalCard = MedicalCard::createMedicalCard($originalData);

        // Act
        $updatedMedicalCard = MedicalCard::updateMedicalCard($originalData['national_number'], $updatedData);

        // Assert
        $this->assertInstanceOf(MedicalCard::class, $updatedMedicalCard);

        // Vérifie si la carte médicale a été correctement mise à jour dans la base de données
        $this->assertDatabaseHas('medical_card', [
            'national_number' => '123456789',
            'first_name' => 'Updated John',
            'tetanos_protected' => true,
            'medecins' => 'Paracetamol',
            'postal_code' => '54321',
        ]);

    }

    public function testDeleteMedicalCard()
    {
        // Arrange
        $nationalNumberToDelete = '111111111';

        // Act
        $result = MedicalCard::deleteMedicalCard($nationalNumberToDelete);

        // Assert
        $this->assertTrue($result);

        // Vérifiez si la carte médicale a été correctement supprimée de la base de données
        $this->assertDatabaseMissing('medical_card', ['national_number' => $nationalNumberToDelete]);
    }

    public function testGetMedicalCard()
{
    // Arrange
    $nationalNumberToRetrieve = '111111111';

    // Act
    $retrievedMedicalCard = MedicalCard::getMedicalCardById($nationalNumberToRetrieve);

    // Assert
    $this->assertInstanceOf(MedicalCard::class, $retrievedMedicalCard);
    $this->assertEquals('Jane', $retrievedMedicalCard->first_name);
    
}

public function testGetAllMedicalCards()
{
    // Act
    $allMedicalCards = MedicalCard::getAllMedicalCards();

    // Assert
    $this->assertIsIterable($allMedicalCards);
    $this->assertGreaterThan(0, count($allMedicalCards));

    // You can perform more specific assertions based on your data structure
    foreach ($allMedicalCards as $medicalCard) {
        $this->assertInstanceOf(MedicalCard::class, $medicalCard);
    }
}

public function testGetNonExistingMedicalCard()
{
    // Arrange
    $nationalNumberToRetrieve = '999999999'; // Assuming this doesn't exist

    // Act
    $retrievedMedicalCard = MedicalCard::getMedicalCardById($nationalNumberToRetrieve);

    // Assert
    $this->assertNull($retrievedMedicalCard);
}

public function testUpdateNonExistingMedicalCard()
{
    // Arrange
    $nonExistingNationalNumber = '999999999'; // Assuming this doesn't exist

    $updatedData = [
        'first_name' => 'Updated John',
        'tetanos_protected' => true,
        'medecins' => 'Paracetamol',
        'postal_code' => '54321',
    ];

    // Act
    $updatedMedicalCard = MedicalCard::updateMedicalCard($nonExistingNationalNumber, $updatedData);

    // Assert
    $this->assertNull($updatedMedicalCard);
}

public function testDeleteNonExistingMedicalCard()
{
    // Arrange
    $nonExistingNationalNumber = '999999999'; // Assuming this doesn't exist

    // Act
    $result = MedicalCard::deleteMedicalCard($nonExistingNationalNumber);

    // Assert
    $this->assertFalse($result);
}


    
}
