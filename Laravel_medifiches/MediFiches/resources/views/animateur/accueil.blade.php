@extends('template')
@section('content')
<script type="text/javascript">
        $(document).ready(function() {
            let width = screen.width
            if (width <= 800) {
                $("#banner").attr("src", "{{ asset('images/medi-banner-vertical.png') }}");
            }
        });
</script>

<header style="position: relative;">
    <img src="{{ asset('images/medi-banner.png') }}" id="banner" alt="Banner" class="img-fluid" style="width: 100%; height: auto;">
    <div class="centered">
        <h1 class="text-white">
            <span>MediFiches</span>
        </h1>
    </div>
</header>

@endsection
