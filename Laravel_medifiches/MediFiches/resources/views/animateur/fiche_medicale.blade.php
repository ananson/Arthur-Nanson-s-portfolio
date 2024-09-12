@extends('template')
@section('title', " - Fiche Médicale")
@section('content')
<script type="text/javascript">
  $(document).ready(function() {
    let width = screen.width
    if (width <= 800) {
      $("#banner").attr("src", "{{ asset('images/medi-banner-vertical.png') }}");
    }
  });
</script>

<div class="postition-relative">
  <img src="{{ asset('images/medi-banner.png') }}" id="banner" alt="Banner" class="img-fluid" style="width: 100%; height: auto;">
  <div class="container mt-5 position-absolute bg-white" id="consult">
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h3 class="me-2">Fiche Médicale</h3>
    <form method="POST" action="{{ route('generate-pdf') }}" class="ms-auto">
        @csrf
        <button type="submit" class="btn" style="background: none; border: none;" title="Télécharger en PDF">
            <img src="{{ asset('images/down.png') }}" alt="Générer PDF" style="height: 40px;"> 
        </button>
    </form>
</div>

    <div class="container mb-3">
      <label for="childSelect"><strong>Enfant </strong></label>
      <select class="form-control w-25" id="childSelect"> 
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
        <option value="4">4</option>
      </select>
      <input type="button" class="btn btn-outline-primary mt-1" value="Fiche">
    </div>
    <ul class="list-group list-group-flush">
      <li class="list-group-item"><strong>Nom de l'enfant: </strong> Leblanc</li>
      <li class="list-group-item"><strong>Prénom de l'enfant: </strong> Juste</li>
      <li class="list-group-item"><strong>Date de naissance: </strong> 31/10/1997</li>
      <li class="list-group-item"><strong>Peut nager: </strong> Oui</li>
      <li class="list-group-item"><strong>Médicaments: </strong> Sediplus Sleep</li>
      <li class="list-group-item"><strong>Quantité de médicaments: </strong> 1</li>
      <li class="list-group-item"><strong>Indiquez la fréquence: </strong> Chaque nuit</li>
      <li class="list-group-item"><strong>Allergies: </strong> Aucune</li>
      <li class="list-group-item"><strong>Conséquences des allergies: </strong> Aucune</li>
      <li class="list-group-item"><strong>Vaccination tetanos: </strong> Oui</li>
      <li class="list-group-item"><strong>Adresse: </strong> Rue Royale 67, 1000 Bruxelles</li>
    </ul>
  </div>
</div>
@endsection
