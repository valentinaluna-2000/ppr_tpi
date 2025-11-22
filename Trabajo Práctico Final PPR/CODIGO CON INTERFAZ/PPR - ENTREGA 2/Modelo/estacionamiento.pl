% ==============================
%  Conocimiento base
% ==============================

% Hechos: estado(lugar, estado).
estado(a1, disponible).
estado(a2, ocupado).
estado(a3, disponible).

% Hechos: lugar(lugar, estacionamiento).
lugar(a1, e1).
lugar(a2, e1).
lugar(a3, e1).

% ==============================
%  Reglas lógicas
% ==============================

% Un lugar está disponible si su estado es 'disponible'.
lugarDisponible(Lugar) :-
    estado(Lugar, disponible).

% Existe un lugar disponible en el estacionamiento.
existeLugarDisponible(Estacionamiento) :-
    lugar(Lugar, Estacionamiento),
    lugarDisponible(Lugar).

% El estacionamiento está lleno si NO hay ningún lugar disponible.
estaLleno(Estacionamiento) :-
    not(existeLugarDisponible(Estacionamiento)).